package pl.bussystem.domain.busride.service;

import pl.bussystem.domain.busride.model.dto.CreateBusRideFromScheduleAndDatesDTO;
import pl.bussystem.domain.busride.persistence.entity.BusRideEntity;
import pl.bussystem.domain.busstop.persistence.entity.BusStopEntity;

import java.util.List;

public interface BusRideService {
  BusRideEntity create(BusRideEntity busRideEntity);

  List<BusRideEntity> read();

  List<BusRideEntity> autoCreate(CreateBusRideFromScheduleAndDatesDTO dto);

  BusRideEntity readById(Integer id);

  BusRideEntity update(BusRideEntity busRideEntity);

  List<BusStopEntity> readAllStops(BusRideEntity ride);

  Boolean containConnection(BusRideEntity ride, BusStopEntity stopFrom, BusStopEntity stopTo);
}
