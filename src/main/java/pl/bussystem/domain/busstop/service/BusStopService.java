package pl.bussystem.domain.busstop.service;

import pl.bussystem.domain.busstop.persistence.entity.BusStopEntity;

import java.util.List;

public interface BusStopService {
  BusStopEntity create(BusStopEntity busEntity);

  List<BusStopEntity> read();

  void deleteById(Integer id);

  List<BusStopEntity> readByBusLineId(Integer id);
}
