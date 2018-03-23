package pl.bussystem.domain.busstop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.bussystem.domain.busstop.persistence.entity.BusStopEntity;
import pl.bussystem.domain.busstop.persistence.repository.BusStopRepository;

import java.util.List;

@Service
public class BusStopServiceImpl implements BusStopService {
  private BusStopRepository busStopRepository;

  @Autowired
  public BusStopServiceImpl(BusStopRepository busStopRepository) {
    this.busStopRepository = busStopRepository;
  }

  @Override
  public BusStopEntity addBusStop(BusStopEntity busEntity) {
    String city = busEntity.getCity();
    String name = busEntity.getName();
    if (!busStopRepository.findByCityAndName(city, name).isEmpty()) {
      throw new IllegalArgumentException("bus entity: " + busEntity.toString() + " already exists");
    }
    return busStopRepository.save(busEntity);
  }

  @Override
  public List<BusStopEntity> findAll() {
    return busStopRepository.findAll();
  }
}
