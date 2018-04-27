package pl.bussystem.domain.busride.service;

import lombok.Setter;
import org.joda.time.Days;
import org.joda.time.DurationFieldType;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.bussystem.domain.busride.model.dto.CreateBusRideFromScheduleAndDatesDTO;
import pl.bussystem.domain.busride.persistence.entity.BusRideEntity;
import pl.bussystem.domain.busride.persistence.repository.BusRideRepository;
import pl.bussystem.domain.lineinfo.busline.persistence.entity.BusLineEntity;
import pl.bussystem.domain.lineinfo.busline.persistence.repository.BusLineRepository;
import pl.bussystem.domain.lineinfo.schedule.persistence.entity.ScheduleEntity;
import pl.bussystem.domain.lineinfo.schedule.persistence.repository.ScheduleRepository;

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
  private Clock clock;

  @Autowired
  public BusRideServiceImpl(BusRideRepository busRideRepository,
                            BusLineRepository busLineRepository,
                            ScheduleRepository scheduleRepository,
                            Clock clock) {
    this.busRideRepository = busRideRepository;
    this.busLineRepository = busLineRepository;
    this.scheduleRepository = scheduleRepository;
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

    BusLineEntity busLineEntity = busLineRepository.findById(dto.getBusLine())
        .orElseThrow(() -> new NoSuchElementException("Bus Line with id: " + dto.getBusLine() + " does not exists"));

    List<Integer> idsOfSchedules = dto.getSchedulesIds();

    List<ScheduleEntity> scheduleEntities = idsOfSchedules.stream()
        .map(id -> scheduleRepository.findById(id)
            .orElseThrow(() -> new NoSuchElementException("Schedule with id: " + id + "does not exists")))
        .collect(toList());

    List<BusRideEntity> createdBusRides = new ArrayList<>();

    for (ScheduleEntity schedule : scheduleEntities) {
      if (!schedule.getEnabled()) continue;

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

    int days = Days.daysBetween(startDate, endDate).getDays();

    List<DayOfWeek> daysFromCode = getDaysOfWeek(scheduleCode);

    for (int i = 0; i <= days; i++) {
      LocalDate day = startDate.withFieldAdded(DurationFieldType.days(), i);
      java.time.LocalDate javaDay = java.time.LocalDate.of(day.getYear(), day.getMonthOfYear(), day.getDayOfMonth());

      if (daysFromCode.stream()
          .map(DayOfWeek::getValue)
          .noneMatch(integer -> integer.equals(day.getDayOfWeek()))) {
        continue;
      }

      if (getNow().isAfter(startDateTime)) {
        continue;
      }

      Time startJourneySqlTime = schedule.getStartHour();
      LocalTime startJourneyLocalTime = startJourneySqlTime.toLocalTime();
      LocalTime endJourneyLocalTime = startJourneyLocalTime.plusMinutes(busLineEntity.getDriveTime());

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

    Integer firstNumber = Integer.valueOf(code.substring(0, 1));
    Integer lastNumber = Integer.valueOf(code.substring(2, 3));

    for (int i = firstNumber; i <= lastNumber; i++) {
      days.add(DayOfWeek.of(i));
    }

    return days;
  }
}