package pl.bussystem.domain.busstop.service;

import pl.bussystem.domain.busstop.persistence.entity.BusStopEntity;

public interface BusStopService {
  BusStopEntity addBusStop(BusStopEntity busEntity);
}
