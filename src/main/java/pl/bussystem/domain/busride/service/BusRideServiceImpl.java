package pl.bussystem.domain.busride.service;

import org.joda.time.Days;
import org.joda.time.DurationFieldType;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

import java.sql.Time;
import java.time.Clock;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static java.util.stream.Collectors.toList;

@Service
public class BusRideServiceImpl implements BusRideService {
  private BusRideRepository busRideRepository;
  private BusLineRepository busLineRepository;
  private ScheduleRepository scheduleRepository;
  private TicketRepository ticketRepository;
  private BusStopService busStopService;
  private Clock clock;
  private static final Logger logger = LoggerFactory.getLogger(BusRideServiceImpl.class);

  @Autowired
  public BusRideServiceImpl(BusRideRepository busRideRepository,
                            BusLineRepository busLineRepository,
                            ScheduleRepository scheduleRepository,
                            TicketRepository ticketRepository,
                            BusStopService busStopService,
                            Clock clock) {
    this.busRideRepository = busRideRepository;
    this.busLineRepository = busLineRepository;
    this.scheduleRepository = scheduleRepository;
    this.ticketRepository = ticketRepository;
    this.busStopService = busStopService;
    this.clock = clock;
  }

  @Override
  public BusRideEntity create(BusRideEntity busRideEntity) {
    return busRideRepository.save(busRideEntity);
  }

  @Override
  public List<BusRideEntity> read() {
    return busRideRepository.findAll();
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

    return read().stream()
        .filter(ride -> ride.getStartDateTime().toLocalDate().equals(date))
        .filter(ride -> ride.getStartDateTime().isAfter(minimalTime))
        .filter(ride -> containConnection(ride, stopFrom, stopTo))
        .filter(ride -> getFreeSeats(ride) >= seats)
        .filter(BusRideEntity::getActive)
        .collect(toList());
  }

  @Override
  public Double calculateTicketPrice(BusRideEntity busRideEntity, BusStopEntity stopFrom, BusStopEntity stopTo) {
    /* need calculator logix */
    return busRideEntity.getDriveNettoPrice();
  }
}