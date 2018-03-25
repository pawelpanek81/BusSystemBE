package pl.bussystem.domain.busstop.service;

import org.springframework.http.ResponseEntity;
import pl.bussystem.domain.busstop.persistence.entity.BusStopEntity;

import java.util.List;

public interface BusStopService {
  BusStopEntity addBusStop(BusStopEntity busEntity);

  List<BusStopEntity> findAll();

  Boolean removeBusStop(Integer id);
}
