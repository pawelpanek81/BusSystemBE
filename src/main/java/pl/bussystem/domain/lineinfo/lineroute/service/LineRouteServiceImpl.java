package pl.bussystem.domain.lineinfo.lineroute.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import pl.bussystem.domain.lineinfo.busline.service.BusLineService;
import pl.bussystem.domain.lineinfo.lineroute.exception.BusLineContainsBusStopException;
import pl.bussystem.domain.lineinfo.lineroute.exception.InvalidDriveTimeException;
import pl.bussystem.domain.lineinfo.lineroute.exception.RouteSequenceGreaterThanLastPlusOneException;
import pl.bussystem.domain.lineinfo.lineroute.exception.RouteSequenceLessThan2Exception;
import pl.bussystem.domain.lineinfo.lineroute.persistence.entity.LineRouteEntity;
import pl.bussystem.domain.lineinfo.lineroute.persistence.repository.LineRouteRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LineRouteServiceImpl implements LineRouteService {
  private static final int MINIMUM_SEQUENCE = 2;
  private static final int MINIMUM_DRIVE_TIME = 0;
  private LineRouteRepository lineRouteRepository;
  private BusLineService busLineService;

  @Autowired
  public LineRouteServiceImpl(LineRouteRepository lineRouteRepository,
                              BusLineService busLineService) {
    this.lineRouteRepository = lineRouteRepository;
    this.busLineService = busLineService;
  }

  @Override
  @CacheEvict("busStops")
  public LineRouteEntity create(LineRouteEntity lineRouteEntity) {
    List<LineRouteEntity> busLineRoutes = this.readByBusLineId(lineRouteEntity.getBusLine().getId());
    busLineRoutes.sort(((o1, o2) -> o2.getSequence() - o1.getSequence()));
    Integer lineRouteSequence = lineRouteEntity.getSequence();
    Integer lineRouteDriveTime = lineRouteEntity.getDriveTime();

    if (busLineRoutes.size() == 0 && lineRouteEntity.getSequence() != 2) {
      throw new RuntimeException("First sequence must be 2");
    }


    if (lineRouteSequence < MINIMUM_SEQUENCE) {
      throw new RouteSequenceLessThan2Exception("Route sequence must be greater or equal 2");
    }

    if (lineRouteDriveTime < MINIMUM_DRIVE_TIME) {
      throw new InvalidDriveTimeException("Drive time must be greater or equal 0");
    }

    if (busLineRoutes.size() > 0) {
      if (lineRouteSequence > busLineRoutes.get(0).getSequence() + 1) {
        throw new RouteSequenceGreaterThanLastPlusOneException("Route sequence cannot be greater than last sequence  + 1");
      }
      if (busLineRoutes.stream()
          .anyMatch(lr -> lr.getBusStop().getId().equals(lineRouteEntity.getBusStop().getId()))) {
        throw new BusLineContainsBusStopException("This bus line already contains this bus stop");
      }
      if (busLineRoutes.stream()
          .anyMatch(lr -> lr.getSequence() >= lineRouteSequence && lr.getDriveTime() < lineRouteDriveTime)) {
        throw new InvalidDriveTimeException("You should drive to this stop before next stop");
      }
      if (busLineRoutes.stream()
          .anyMatch(lr -> lr.getSequence() < lineRouteSequence &&
              lr.getDriveTime() > lineRouteDriveTime)) {
        throw new InvalidDriveTimeException("You should drive to this stop after previous stop");
      }
      for (LineRouteEntity lineRoute : busLineRoutes) {
        if (lineRoute.getSequence() >= lineRouteSequence) {
          lineRoute.setSequence(lineRoute.getSequence() + 1);
          lineRouteRepository.saveAndFlush(lineRoute);
        }
      }
    }

    return lineRouteRepository.saveAndFlush(lineRouteEntity);
  }

  @Override
  public List<LineRouteEntity> read() {
    return lineRouteRepository.findAllByOrderBySequence();
  }

  @Override
  @CacheEvict("busStops")
  public void deleteByBusLineIdAndLineRouteId(Integer busLineId, Integer lineRouteId) {
    Optional<LineRouteEntity> optionalOfLineRouteEntity =
        lineRouteRepository.findOneByBusLineIdAndId(busLineId, lineRouteId);

    if (!optionalOfLineRouteEntity.isPresent()) {
      throw new NoSuchElementException("Line route with bus line id: " + busLineId +
          " and line route id: " + lineRouteId + " does not exists!");
    }

    LineRouteEntity lineRouteEntity = optionalOfLineRouteEntity.get();

    List<LineRouteEntity> busLineRoutes = this.readByBusLineId(lineRouteEntity.getBusLine().getId());
    lineRouteRepository.delete(lineRouteEntity);
    for (int i = lineRouteEntity.getSequence() - 1; i < busLineRoutes.size(); i++) {
      LineRouteEntity lineRoute = busLineRoutes.get(i);
      lineRoute.setSequence(lineRoute.getSequence() - 1);
      lineRouteRepository.saveAndFlush(lineRoute);
    }

  }

  @Override
  public List<LineRouteEntity> readByBusLineId(Integer id) {
    if (busLineService.notExistsById(id)) {
      throw new NoSuchElementException("Bus line with id: " + id + " does not exists!");
    }
    List<LineRouteEntity> allLineRoutes = this.read();
    return allLineRoutes.stream()
        .filter(lr -> lr.getBusLine().getId().equals(id))
        .collect(Collectors.toList());
  }

  @Override
  public LineRouteEntity readById(Integer id) {
    return lineRouteRepository
        .findById(id)
        .orElseThrow(() -> new NoSuchElementException("Line route with id: " + id + " does not exists!"));
  }
}
