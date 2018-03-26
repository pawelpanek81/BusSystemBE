package pl.bussystem.domain.busstop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import pl.bussystem.domain.busstop.persistence.entity.BusStopEntity;
import pl.bussystem.domain.busstop.persistence.repository.BusStopRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class BusStopServiceImpl implements BusStopService {
  private BusStopRepository busStopRepository;

  @Autowired
  public BusStopServiceImpl(BusStopRepository busStopRepository) {
    this.busStopRepository = busStopRepository;
  }

  @Override
  public BusStopEntity create(BusStopEntity busEntity) {
    String city = busEntity.getCity();
    String name = busEntity.getName();
    if (!busStopRepository.findByCityAndName(city, name).isEmpty()) {
      throw new IllegalArgumentException("Bus entity: " + busEntity.toString() + " already exists");
    }
    return busStopRepository.save(busEntity);
  }

  @Override
  public List<BusStopEntity> read() {
    return busStopRepository.findAll();
  }

  @Override
  public void deleteById(Integer id) {
    try {
      busStopRepository.deleteById(id);
    } catch (EmptyResultDataAccessException e) {
      throw new NoSuchElementException("Bus stop with id: " + id + " does not exists!");
    }
  }
}
