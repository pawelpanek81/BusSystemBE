package pl.bussystem.domain.busride.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import pl.bussystem.domain.busride.model.dto.CreateBusRideFromScheduleAndDatesDTO;
import pl.bussystem.domain.busride.persistence.entity.BusRideEntity;
import pl.bussystem.domain.busride.persistence.repository.BusRideRepository;
import pl.bussystem.domain.lineinfo.busline.persistence.entity.BusLineEntity;
import pl.bussystem.domain.lineinfo.busline.persistence.repository.BusLineRepository;
import pl.bussystem.domain.lineinfo.lineroute.service.LineRouteService;
import pl.bussystem.domain.lineinfo.schedule.persistence.entity.ScheduleEntity;
import pl.bussystem.domain.lineinfo.schedule.persistence.repository.ScheduleRepository;
import pl.bussystem.domain.ticket.persistence.repository.TicketRepository;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BusRideServiceImplTest {
  private BusRideService busRideService;

  @Mock
  private BusLineRepository busLineRepository;

  @Mock
  private ScheduleRepository scheduleRepository;

  @Mock
  private BusRideRepository busRideRepository;

  @Mock
  private TicketRepository tickerRepository;

  @Mock
  private LineRouteService lineRouteService;

  @Before
  public void setUp() {
    busRideService = new BusRideServiceImpl(busRideRepository, busLineRepository, scheduleRepository, tickerRepository, lineRouteService, busStopService);
  }

  @Test
  public void autoCreateSchoudlReturnListOfBusRidesBasedOnSchedulesAndDates() {
    //arange
    BusLineEntity n1 = new BusLineEntity(
        1, "N1", null, null, 60
    );
    ScheduleEntity schedule = new ScheduleEntity(1,
        n1, "1-7", Time.valueOf(LocalTime.of(8, 0, 0)), true, 1000.0);
    LocalDateTime startDateTime = LocalDateTime.of(
        LocalDate.of(2018, 4, 25),
        LocalTime.of(7, 0, 0)
    );

    LocalDateTime endDateTime = LocalDateTime.of(
        LocalDate.of(2018, 5, 2),
        LocalTime.of(18, 0, 0)
    );

    CreateBusRideFromScheduleAndDatesDTO dto = new CreateBusRideFromScheduleAndDatesDTO(
        1, startDateTime, endDateTime, Collections.singletonList(1));

    List<BusRideEntity> expected = new ArrayList<BusRideEntity>() {{
      add(new BusRideEntity(
          1,
          LocalDateTime.of(
              LocalDate.of(2018, 4, 25),
              LocalTime.of(8, 0, 0)
          ),
          LocalDateTime.of(
              LocalDate.of(2018, 4, 25),
              LocalTime.of(9, 0, 0)
          ),
          n1,
          null,
          null,
          1000.0,
          null));
      add(new BusRideEntity(
          2,
          LocalDateTime.of(
              LocalDate.of(2018, 4, 26),
              LocalTime.of(8, 0, 0)
          ),
          LocalDateTime.of(
              LocalDate.of(2018, 4, 26),
              LocalTime.of(9, 0, 0)
          ),
          n1,
          null,
          null,
          1000.0,
          null));
      add(new BusRideEntity(
          3,
          LocalDateTime.of(
              LocalDate.of(2018, 4, 27),
              LocalTime.of(8, 0, 0)
          ),
          LocalDateTime.of(
              LocalDate.of(2018, 4, 27),
              LocalTime.of(9, 0, 0)
          ),
          n1,
          null,
          null,
          1000.0,
          null));
      add(new BusRideEntity(
          4,
          LocalDateTime.of(
              LocalDate.of(2018, 4, 28),
              LocalTime.of(8, 0, 0)
          ),
          LocalDateTime.of(
              LocalDate.of(2018, 4, 28),
              LocalTime.of(9, 0, 0)
          ),
          n1,
          null,
          null,
          1000.0,
          null));
      add(new BusRideEntity(
          5,
          LocalDateTime.of(
              LocalDate.of(2018, 4, 29),
              LocalTime.of(8, 0, 0)
          ),
          LocalDateTime.of(
              LocalDate.of(2018, 4, 29),
              LocalTime.of(9, 0, 0)
          ),
          n1,
          null,
          null,
          1000.0,
          null));
      add(new BusRideEntity(
          6,
          LocalDateTime.of(
              LocalDate.of(2018, 4, 30),
              LocalTime.of(8, 0, 0)
          ),
          LocalDateTime.of(
              LocalDate.of(2018, 4, 30),
              LocalTime.of(9, 0, 0)
          ),
          n1,
          null,
          null,
          1000.0,
          null));
      add(new BusRideEntity(
          7,
          LocalDateTime.of(
              LocalDate.of(2018, 5, 1),
              LocalTime.of(8, 0, 0)
          ),
          LocalDateTime.of(
              LocalDate.of(2018, 5, 1),
              LocalTime.of(9, 0, 0)
          ),
          n1,
          null,
          null,
          1000.0,
          null));
      add(new BusRideEntity(
          8,
          LocalDateTime.of(
              LocalDate.of(2018, 5, 2),
              LocalTime.of(8, 0, 0)
          ),
          LocalDateTime.of(
              LocalDate.of(2018, 5, 2),
              LocalTime.of(9, 0, 0)
          ),
          n1,
          null,
          null,
          1000.0,
          null));
    }};

    when(busLineRepository.findById(1)).thenReturn(Optional.of(n1));
    when(scheduleRepository.findById(1)).thenReturn(Optional.of(schedule));


    //act
    List<BusRideEntity> actual = busRideService.autoCreate(dto);

    //assert
    assertEquals(expected.get(0).getStartDateTime(), actual.get(0).getStartDateTime());
    assertEquals(expected.get(0).getEndDateTime(), actual.get(0).getEndDateTime());

    assertEquals(expected.get(1).getStartDateTime(), actual.get(1).getStartDateTime());
    assertEquals(expected.get(1).getEndDateTime(), actual.get(1).getEndDateTime());

    assertEquals(expected.get(2).getStartDateTime(), actual.get(2).getStartDateTime());
    assertEquals(expected.get(2).getEndDateTime(), actual.get(2).getEndDateTime());

    assertEquals(expected.get(3).getStartDateTime(), actual.get(3).getStartDateTime());
    assertEquals(expected.get(3).getEndDateTime(), actual.get(3).getEndDateTime());

    assertEquals(expected.get(4).getStartDateTime(), actual.get(4).getStartDateTime());
    assertEquals(expected.get(4).getEndDateTime(), actual.get(4).getEndDateTime());

    assertEquals(expected.get(5).getStartDateTime(), actual.get(5).getStartDateTime());
    assertEquals(expected.get(5).getEndDateTime(), actual.get(5).getEndDateTime());

    assertEquals(expected.get(6).getStartDateTime(), actual.get(6).getStartDateTime());
    assertEquals(expected.get(6).getEndDateTime(), actual.get(6).getEndDateTime());

    assertEquals(expected.get(7).getStartDateTime(), actual.get(7).getStartDateTime());
    assertEquals(expected.get(7).getEndDateTime(), actual.get(7).getEndDateTime());

    for (BusRideEntity entity : expected) {
      assertEquals(entity.getDriveNettoPrice(), new Double(1000.0));
      assertEquals(entity.getBusLine().getId(), new Integer(1));
    }
  }
}