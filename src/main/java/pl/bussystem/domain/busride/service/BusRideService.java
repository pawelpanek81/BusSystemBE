package pl.bussystem.domain.busride.service;

import pl.bussystem.domain.busride.persistence.entity.BusRideEntity;

import java.util.List;

public interface BusRideService {
  BusRideEntity create(BusRideEntity busRideEntity);

  List<BusRideEntity> read();

  List<BusRideEntity> readUpcomingRides();
}
