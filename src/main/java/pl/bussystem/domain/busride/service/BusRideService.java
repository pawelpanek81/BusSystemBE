package pl.bussystem.domain.busride.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.bussystem.domain.busride.model.dto.CreateBusRideFromScheduleAndDatesDTO;
import pl.bussystem.domain.busride.persistence.entity.BusRideEntity;
import pl.bussystem.domain.busstop.persistence.entity.BusStopEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface BusRideService {
  BusRideEntity create(BusRideEntity busRideEntity);

  Page<BusRideEntity> read(Pageable pageable);

  List<BusRideEntity> autoCreate(CreateBusRideFromScheduleAndDatesDTO dto);

  BusRideEntity readById(Integer id);

  BusRideEntity update(BusRideEntity busRideEntity);

  Boolean containConnection(BusRideEntity ride, BusStopEntity stopFrom, BusStopEntity stopTo);

  Integer getFreeSeats(BusRideEntity ride);

  List<BusRideEntity> readActiveRidesFromToWhereEnoughtSeats(BusStopEntity stopFrom, BusStopEntity stopTo,
                                                             LocalDate date, Integer seats,
                                                             LocalDateTime minimalTime);
  Double calculateTicketPrice(BusRideEntity busRideEntity, BusStopEntity stopFrom, BusStopEntity stopTo);

  List<BusRideEntity> readActive();

  Page<BusRideEntity> readInactive(Pageable pageable);

  BusRideEntity patch(Integer id, Map<String, Object> fields);
}
