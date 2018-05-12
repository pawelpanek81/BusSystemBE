package pl.bussystem.domain.busride.service;

import org.joda.time.Days;
import org.joda.time.DurationFieldType;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;
import pl.bussystem.domain.bus.persistence.repository.BusRepository;
import pl.bussystem.domain.busride.model.dto.CreateBusRideFromScheduleAndDatesDTO;
import pl.bussystem.domain.busride.persistence.entity.BusRideEntity;
import pl.bussystem.domain.busride.persistence.repository.BusRideRepository;
import pl.bussystem.domain.busstop.persistence.entity.BusStopEntity;
import pl.bussystem.domain.busstop.service.BusStopService;
import pl.bussystem.domain.lineinfo.busline.persistence.entity.BusLineEntity;
import pl.bussystem.domain.lineinfo.busline.persistence.repository.BusLineRepository;
import pl.bussystem.domain.lineinfo.schedule.persistence.entity.ScheduleEntity;
import pl.bussystem.domain.lineinfo.schedule.persistence.repository.ScheduleRepository;
import pl.bussystem.domain.ticket.persistence.repository.TicketRepository;
import pl.bussystem.domain.user.persistence.repository.AccountRepository;

import java.lang.reflect.Field;
import java.sql.Time;
import java.time.Clock;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import static java.util.stream.Collectors.toList;

@Service
public class BusRideServiceImpl implements BusRideService {
  private BusRideRepository busRideRepository;
  private BusLineRepository busLineRepository;
  private ScheduleRepository scheduleRepository;
  private TicketRepository ticketRepository;
  private BusStopService busStopService;
  private AccountRepository accountRepository;
  private BusRepository busRepository;
  private Clock clock;
  private static final Logger logger = LoggerFactory.getLogger(BusRideServiceImpl.class);

  @Autowired
  public BusRideServiceImpl(BusRideRepository busRideRepository,
                            BusLineRepository busLineRepository,
                            ScheduleRepository scheduleRepository,
                            TicketRepository ticketRepository,
                            BusStopService busStopService,
                            AccountRepository accountRepository,
                            BusRepository busRepository,
                            Clock clock) {
    this.busRideRepository = busRideRepository;
    this.busLineRepository = busLineRepository;
    this.scheduleRepository = scheduleRepository;
    this.ticketRepository = ticketRepository;
    this.busStopService = busStopService;
    this.accountRepository = accountRepository;
    this.busRepository = busRepository;
    this.clock = clock;
  }

  @Override
  public BusRideEntity create(BusRideEntity busRideEntity) {
    return busRideRepository.save(busRideEntity);
  }

  @Override
  public Page<BusRideEntity> read(Pageable pageable) {
    return busRideRepository.findAllByOrderByStartDateTimeAsc(pageable);
  }

  @Override
  public List<BusRideEntity> autoCreate(CreateBusRideFromScheduleAndDatesDTO dto) {
    LocalDateTime startDateTimePoint = dto.getStartDateTime();
    LocalDateTime endDateTimePoint = dto.getEndDateTime();
    logger.info("autoCreate: startDateTimePoint: " + startDateTimePoint.toString());
    logger.info("autoCreate: endDateTimePoint: " + endDateTimePoint.toString());

    BusLineEntity busLineEntity = busLineRepository.findById(dto.getBusLine())
        .orElseThrow(() -> new NoSuchElementException("Bus Line with id: " + dto.getBusLine() + " does not exists"));

    List<Integer> idsOfSchedules = dto.getSchedulesIds();

    List<ScheduleEntity> scheduleEntities = idsOfSchedules.stream()
        .map(id -> scheduleRepository.findById(id)
            .orElseThrow(() -> new NoSuchElementException("Schedule with id: " + id + "does not exists")))
        .collect(toList());

    List<BusRideEntity> createdBusRides = new ArrayList<>();

    for (ScheduleEntity schedule : scheduleEntities) {
      if (!schedule.getEnabled()) {
        throw new IllegalArgumentException("Schedule must be actived");
      }

      List<BusRideEntity> ridesFromSchedule = this.getRidesFromSchedule(
          schedule,
          startDateTimePoint,
          endDateTimePoint,
          busLineEntity
      );
      createdBusRides.addAll(ridesFromSchedule);
    }

    createdBusRides.forEach(entity -> busRideRepository.save(entity));

    return createdBusRides;
  }

  @Override
  public BusRideEntity readById(Integer id) {
    return busRideRepository.findById(id)
        .orElseThrow(() -> new NoSuchElementException("Bus Ride with id: " + id + " does not exists"));
  }

  @Override
  public BusRideEntity update(BusRideEntity busRideEntity) {
    return busRideRepository.save(busRideEntity);
  }

  private List<BusRideEntity> getRidesFromSchedule(ScheduleEntity schedule,
                                                   LocalDateTime startDateTime,
                                                   LocalDateTime endDateTime,
                                                   BusLineEntity busLineEntity) {
    String scheduleCode = schedule.getCode();
    Double driveNettoPrice = schedule.getDriveNettoPrice();
    Boolean scheduleEnabled = schedule.getEnabled();

    if (!scheduleEnabled) throw new IllegalArgumentException("Schedule must be enabled");

    List<BusRideEntity> returnedBusRides = new ArrayList<>();

    LocalDate startDate = new LocalDate(startDateTime.getYear(), startDateTime.getMonthValue(), startDateTime.getDayOfMonth());
    LocalDate endDate = new LocalDate(endDateTime.getYear(), endDateTime.getMonthValue(), endDateTime.getDayOfMonth());

    logger.info("getRidesFromSchedule() startDate " + startDate.toString());
    logger.info("getRidesFromSchedule() endDate " + endDate.toString());

    int days = Days.daysBetween(startDate, endDate).getDays();

    List<DayOfWeek> daysFromCode = getDaysOfWeek(scheduleCode);

    for (int i = 0; i <= days; i++) {
      LocalDate day = startDate.withFieldAdded(DurationFieldType.days(), i);
      java.time.LocalDate javaDay = java.time.LocalDate.of(day.getYear(), day.getMonthOfYear(), day.getDayOfMonth());

      if (getNow().isAfter(startDateTime)) {
        continue;
      }

      if (daysFromCode.stream()
          .map(DayOfWeek::getValue)
          .noneMatch(integer -> integer.equals(day.getDayOfWeek()))) {
        continue;
      }

      Time startJourneySqlTime = schedule.getStartHour();
      LocalTime startJourneyLocalTime = startJourneySqlTime.toLocalTime();
      LocalTime endJourneyLocalTime = startJourneyLocalTime.plusMinutes(busLineEntity.getDriveTime());

      if (LocalDateTime.of(javaDay, startJourneyLocalTime).isBefore(startDateTime)) {
        continue;
      }

      if (LocalDateTime.of(javaDay, endJourneyLocalTime).isAfter(endDateTime)) {
        continue;
      }

      returnedBusRides.add(
          BusRideEntity.builder()
              .busLine(busLineEntity)
              .driveNettoPrice(driveNettoPrice)
              .startDateTime(
                  LocalDateTime.of(javaDay, startJourneyLocalTime)
              )
              .endDateTime(
                  LocalDateTime.of(javaDay, endJourneyLocalTime)
              )
              .active(false)
              .build()
      );
    }
    return returnedBusRides;
  }

  private LocalDateTime getNow() {
    return LocalDateTime.now(clock);
  }

  private List<DayOfWeek> getDaysOfWeek(String code) {
    List<DayOfWeek> days = new ArrayList<>();

    if (code.contains("-")) {
      Integer firstNumber = Integer.valueOf(code.substring(0, 1));
      Integer lastNumber = Integer.valueOf(code.substring(2, 3));
      for (int i = firstNumber; i <= lastNumber; i++) {
        days.add(DayOfWeek.of(i));
      }

    } else {
      String[] numbers = code.split(",");
      for (String i : numbers) {
        days.add(DayOfWeek.of(Integer.valueOf(i)));
      }
    }

    return days;
  }

  @Override
  public Boolean containConnection(BusRideEntity ride, BusStopEntity stopFrom, BusStopEntity stopTo) {
    List<BusStopEntity> busStopEntities = busStopService.readByBusLineId(ride.getBusLine().getId());
    Integer fromIndex = busStopEntities.indexOf(stopFrom);
    Integer toIndex = busStopEntities.indexOf(stopTo);
    return fromIndex != -1 && toIndex != -1 && fromIndex < toIndex;
  }

  @Override
  public Integer getFreeSeats(BusRideEntity ride) {
    return ride.getBus().getSeats() - ticketRepository.findByBusRide(ride).size();
  }

  @Override
  public List<BusRideEntity> readActiveRidesFromToWhereEnoughtSeats(BusStopEntity stopFrom, BusStopEntity stopTo,
                                                                    java.time.LocalDate date, Integer seats,
                                                                    LocalDateTime minimalTime) {

    return readActive().stream()
        .filter(ride -> ride.getStartDateTime().toLocalDate().equals(date))
        .filter(ride -> ride.getStartDateTime().isAfter(minimalTime))
        .filter(ride -> containConnection(ride, stopFrom, stopTo))
        .filter(ride -> getFreeSeats(ride) >= seats)
        .collect(toList());
  }

  @Override
  public Double calculateTicketPrice(BusRideEntity busRideEntity, BusStopEntity stopFrom, BusStopEntity stopTo) {
    /* need calculator logix */
    return busRideEntity.getDriveNettoPrice() / busRideEntity.getBus().getSeats();
  }

  @Override
  public List<BusRideEntity> readActive() {
    return busRideRepository.findAllByActiveOrderByStartDateTimeAsc(Boolean.TRUE);
  }

  @Override
  public Page<BusRideEntity> readInactive(Pageable pageable) {
    return busRideRepository.findAllByActiveOrderByStartDateTimeAsc(pageable, Boolean.FALSE);
  }

  @Override
  public BusRideEntity patch(Integer id, Map<String, Object> fields) {
    BusRideEntity busRideEntity = this.readById(id);

    fields.forEach((k, v) -> {
      Field field = ReflectionUtils.findField(BusRideEntity.class, k);
      field.setAccessible(true);

      switch (k) {
        case "primaryDriver":
        case "secondaryDriver":
          if (v == null) {
            ReflectionUtils.setField(field, busRideEntity, null);
          } else {
            v = accountRepository.findById((Integer) v).orElse(null);
            ReflectionUtils.setField(field, busRideEntity, v);
          }
          break;
        case "active":
          if (v == null) throw new IllegalArgumentException();
          ReflectionUtils.setField(field, busRideEntity, v);
          break;
        case "driveNettoPrice":
          if (v == null) throw new IllegalArgumentException();
          ReflectionUtils.setField(field, busRideEntity, Double.valueOf(String.valueOf(v)));
          break;
        case "bus":
          if (v == null) {
            ReflectionUtils.setField(field, busRideEntity, null);
          } else {
            v = busRepository.findById((Integer) v).orElse(null);
            ReflectionUtils.setField(field, busRideEntity, v);
          }
      }
      field.setAccessible(false);
    });

    this.update(busRideEntity);
    return busRideEntity;
  }

  @Override
  public Page<BusRideEntity> getBusRidesPagesByTypeAndPeriod(String type, String period, Pageable page) {
    Page<BusRideEntity> busRides = new PageImpl<>(new ArrayList<>());
    if (type == null) {
      if (period == null) {
        busRides = this.read(page);
      } else if (period.equals("week")) {
        busRides = this.readBeforeDateAndAfterNow(page, LocalDateTime.now().plusWeeks(1));
      } else if (period.equals("month")) {
        busRides = this.readBeforeDateAndAfterNow(page, LocalDateTime.now().plusMonths(1));
      }
    } else if (type.equals("active")) {
      List<BusRideEntity> activeBusRides = this.readActive();
      if (period == null) {
        int start = (int) page.getOffset();
        int end = (start + page.getPageSize()) > activeBusRides.size() ? activeBusRides.size() : (start + page.getPageSize());
        busRides = new PageImpl<>(activeBusRides.subList(start, end), page, activeBusRides.size());
      } else if (period.equals("week")) {
          List<BusRideEntity> weekActiveBusRides = activeBusRides.stream()
              .filter(br -> br.getStartDateTime().isAfter(LocalDateTime.now()))
            .filter(br -> br.getStartDateTime().isBefore(LocalDateTime.now().plusWeeks(1)))
            .collect(toList());
        int start = (int) page.getOffset();
        int end = (start + page.getPageSize()) > weekActiveBusRides.size() ? weekActiveBusRides.size() : (start + page.getPageSize());
        busRides = new PageImpl<>(weekActiveBusRides.subList(start, end), page, weekActiveBusRides.size());
      } else if (period.equals("month")) {
        List<BusRideEntity> monthActiveBusRides = activeBusRides.stream()
            .filter(br -> br.getStartDateTime().isAfter(LocalDateTime.now()))
            .filter(br -> br.getStartDateTime().isBefore(LocalDateTime.now().plusMonths(1)))
            .collect(toList());
        int start = (int) page.getOffset();
        int end = (start + page.getPageSize()) > monthActiveBusRides.size() ? monthActiveBusRides.size() : (start + page.getPageSize());
        busRides = new PageImpl<>(monthActiveBusRides.subList(start, end), page, monthActiveBusRides.size());
      }
    } else if (type.equals("inactive")) {
      if (period == null) {
        busRides = this.readInactive(page);
      } else if (period.equals("week")) {
        busRides = this.readInactiveBeforeDateAndAfterNow(page, LocalDateTime.now().plusWeeks(1));
      } else if (period.equals("month")) {
        busRides = this.readInactiveBeforeDateAndAfterNow(page, LocalDateTime.now().plusMonths(1));
      }
    }
    return busRides;
  }

  @Override
  public Page<BusRideEntity> readBeforeDateAndAfterNow(Pageable page, LocalDateTime localDateTime) {
    return busRideRepository
        .findAllByStartDateTimeAfterAndStartDateTimeBeforeOrderByStartDateTimeAsc(
            page,
            LocalDateTime.now(),
            localDateTime
        );
  }

  @Override
  public Page<BusRideEntity> readInactiveBeforeDateAndAfterNow(Pageable page, LocalDateTime localDateTime) {
    return busRideRepository
        .findAllByActiveAndStartDateTimeAfterAndStartDateTimeBeforeOrderByStartDateTimeAsc(
            page,
            Boolean.FALSE,
            LocalDateTime.now(),
            localDateTime
        );
  }

}