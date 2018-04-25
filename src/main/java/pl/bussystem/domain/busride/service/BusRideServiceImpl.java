package pl.bussystem.domain.busride.service;

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
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class BusRideServiceImpl implements BusRideService {
  private BusRideRepository busRideRepository;
  private BusLineRepository busLineRepository;
  private ScheduleRepository scheduleRepository;

  @Autowired
  public BusRideServiceImpl(BusRideRepository busRideRepository,
                            BusLineRepository busLineRepository,
                            ScheduleRepository scheduleRepository) {
    this.busRideRepository = busRideRepository;
    this.busLineRepository = busLineRepository;
    this.scheduleRepository = scheduleRepository;
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
    LocalDateTime startDateTime = dto.getStartDateTime();
    LocalDateTime endDateTime = dto.getEndDateTime();

    BusLineEntity busLineEntity = busLineRepository.findById(dto.getBusLine()).orElse(null);

    List<Integer> schedulesIds = dto.getSchedulesIds();

    List<ScheduleEntity> scheduleEntities = schedulesIds.stream()
        .map(id -> scheduleRepository.findById(id).orElse(null))
        .collect(toList());

    List<BusRideEntity> returnedBusRides = new ArrayList<>();

    for (ScheduleEntity schedule : scheduleEntities) {
      List<BusRideEntity> ridesFromSchedule = this.getRidesFromSchedule(
          schedule,
          startDateTime,
          endDateTime,
          busLineEntity
      );
      returnedBusRides.addAll(ridesFromSchedule);
    }

    for (BusRideEntity entity : returnedBusRides) {
      busRideRepository.save(entity);
    }

    return returnedBusRides;
  }

  private List<BusRideEntity> getRidesFromSchedule(ScheduleEntity schedule,
                                                   LocalDateTime startDateTime,
                                                   LocalDateTime endDateTime,
                                                   BusLineEntity busLineEntity) {
    Time startHour = schedule.getStartHour();
    String code = schedule.getCode();
    Double driveNettoPrice = schedule.getDriveNettoPrice();
    Boolean enabled = schedule.getEnabled();

    List<BusRideEntity> returnedBusRides = new ArrayList<>();

    LocalDate startDate = new LocalDate(startDateTime.getYear(), startDateTime.getMonthValue(), startDateTime.getDayOfMonth());
    LocalDate endDate = new LocalDate(endDateTime.getYear(), endDateTime.getMonthValue(), endDateTime.getDayOfMonth());

    int days = Days.daysBetween(startDate, endDate).getDays();
    List<LocalDate> dates = new ArrayList<LocalDate>(days);
    for (int i = 0; i <= days; i++) {
      LocalDate d = startDate.withFieldAdded(DurationFieldType.days(), i);

      Time endJourneyLocalTime = schedule.getStartHour();

      LocalTime localtime = endJourneyLocalTime.toLocalTime();
      localtime = localtime.plusMinutes(busLineEntity.getDriveTime());
      endJourneyLocalTime = Time.valueOf(localtime);


      returnedBusRides.add(
          BusRideEntity.builder()
              .busLine(busLineEntity)
              .driveNettoPrice(schedule.getDriveNettoPrice())
              .startDateTime(
                  LocalDateTime.of(
                      d.getYear(), d.getMonthOfYear(), d.getDayOfMonth(),
                      schedule.getStartHour().getHours(),
                      schedule.getStartHour().getMinutes(),
                      schedule.getStartHour().getSeconds()
                  )
              )
              .endDateTime(
                  LocalDateTime.of(
                      d.getYear(), d.getMonthOfYear(), d.getDayOfMonth(),
                      endJourneyLocalTime.getHours(),
                      endJourneyLocalTime.getMinutes(),
                      endJourneyLocalTime.getSeconds()
                  )
              )
              .build()
      );
    }
    return returnedBusRides;

  }
}