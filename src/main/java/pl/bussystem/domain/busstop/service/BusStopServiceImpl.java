package pl.bussystem.domain.busstop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.bussystem.domain.busstop.persistence.entity.BusStopEntity;
import pl.bussystem.domain.busstop.persistence.repository.BusStopRepository;

@Service
public class BusStopServiceImpl implements BusStopService {
  private BusStopRepository busStopRepository;

  @Autowired
  public BusStopServiceImpl(BusStopRepository busStopRepository) {
    this.busStopRepository = busStopRepository;
  }

  @Override
  public BusStopEntity addBusStop(BusStopEntity busEntity) {
    return busStopRepository.save(busEntity);
  }
}
