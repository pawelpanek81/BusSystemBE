package pl.bussystem.domain.busstop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import pl.bussystem.domain.busstop.persistence.entity.BusStopEntity;
import pl.bussystem.domain.busstop.persistence.repository.BusStopRepository;
import pl.bussystem.domain.lineroute.persistence.entity.LineRouteEntity;
import pl.bussystem.domain.lineroute.service.LineRouteService;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class BusStopServiceImpl implements BusStopService {
  private BusStopRepository busStopRepository;
  private LineRouteService lineRouteService;

  @Autowired
  public BusStopServiceImpl(BusStopRepository busStopRepository,
                            LineRouteService lineRouteService) {
    this.busStopRepository = busStopRepository;
    this.lineRouteService = lineRouteService;
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

  @Override
  public List<BusStopEntity> readByBusLineId(Integer id) {
    List<LineRouteEntity> lineRouteEntitiesById = lineRouteService.readByBusLineId(id);

    return lineRouteEntitiesById.stream()
        .map(LineRouteEntity::getBusStop)
        .collect(Collectors.toList());

  }
}
