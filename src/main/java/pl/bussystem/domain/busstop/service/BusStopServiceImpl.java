package pl.bussystem.domain.busstop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import pl.bussystem.domain.lineinfo.busline.persistence.entity.BusLineEntity;
import pl.bussystem.domain.lineinfo.busline.service.BusLineService;
import pl.bussystem.domain.busstop.persistence.entity.BusStopEntity;
import pl.bussystem.domain.busstop.persistence.repository.BusStopRepository;
import pl.bussystem.domain.lineinfo.lineroute.persistence.entity.LineRouteEntity;
import pl.bussystem.domain.lineinfo.lineroute.service.LineRouteService;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class BusStopServiceImpl implements BusStopService {
  private BusStopRepository busStopRepository;
  private LineRouteService lineRouteService;
  private BusLineService busLineService;

  @Autowired
  public BusStopServiceImpl(BusStopRepository busStopRepository,
                            LineRouteService lineRouteService,
                            BusLineService busLineService) {
    this.busStopRepository = busStopRepository;
    this.lineRouteService = lineRouteService;
    this.busLineService = busLineService;
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
    BusLineEntity busLine = busLineService.readById(id);
    List<LineRouteEntity> lineRouteEntitiesById = lineRouteService.readByBusLineId(id);
    List<BusStopEntity> busStops = lineRouteEntitiesById.stream()
        .map(LineRouteEntity::getBusStop)
        .collect(Collectors.toList());

    busStops.add(0, busLine.getFrom());
    busStops.add(busLine.getTo());

    return busStops;
  }

  @Override
  public List<BusStopEntity> readBusStopsAvailableFrom(Integer fromId) {
    List<BusStopEntity> busStops = new ArrayList<>();
    List<BusLineEntity> busLines = busLineService.read();
    BusStopEntity from = this.readById(fromId);

    for (BusLineEntity busline : busLines) {
      List<LineRouteEntity> lineRoutes = lineRouteService.readByBusLineId(busline.getId());

      if (busline.getFrom().getId().equals(fromId)) {
        List<BusStopEntity> stopsFromRoute = lineRoutes.stream()
            .map(LineRouteEntity::getBusStop)
            .collect(Collectors.toList());

        busStops.addAll(stopsFromRoute);
        busStops.add(busline.getTo());
      } else {
        for (int i = 0; i < lineRoutes.size(); i++) {
          if (lineRoutes.get(i).getBusStop().getId().equals(from.getId())) {
            List<BusStopEntity> stops = lineRoutes.subList(i + 1, lineRoutes.size()).stream()
                .map(LineRouteEntity::getBusStop)
                .collect(Collectors.toList());

            busStops.addAll(stops);
            busStops.add(busline.getTo());
            break;
          }
        }
      }
    }
    return busStops;
  }

  @Override
  public List<BusStopEntity> readUnusedByBusLineId(Integer id) {
    List<Integer> busStopsIdsUsedByBusLine = this.readByBusLineId(id).stream()
        .map(BusStopEntity::getId)
        .collect(Collectors.toList());

    List<BusStopEntity> allStops = this.read();

    return allStops.stream()
        .filter(bs -> !busStopsIdsUsedByBusLine.contains(bs.getId()))
        .collect(Collectors.toList());
  }

  @Override
  public BusStopEntity readById(Integer fromId) {
    return busStopRepository.findById(fromId).orElse(null);
  }
}
