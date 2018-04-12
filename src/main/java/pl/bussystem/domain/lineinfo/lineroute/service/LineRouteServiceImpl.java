package pl.bussystem.domain.lineinfo.lineroute.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import pl.bussystem.domain.lineinfo.busline.service.BusLineService;
import pl.bussystem.domain.lineinfo.lineroute.persistence.entity.LineRouteEntity;
import pl.bussystem.domain.lineinfo.lineroute.persistence.repository.LineRouteRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class LineRouteServiceImpl implements LineRouteService {
  private LineRouteRepository lineRouteRepository;
  private BusLineService busLineService;

  @Autowired
  public LineRouteServiceImpl(LineRouteRepository lineRouteRepository,
                              BusLineService busLineService) {
    this.lineRouteRepository = lineRouteRepository;
    this.busLineService = busLineService;
  }

  @Override
  public LineRouteEntity create(LineRouteEntity lineRouteEntity) {
    return lineRouteRepository.save(lineRouteEntity);
  }

  @Override
  public List<LineRouteEntity> read() {
    return lineRouteRepository.findAll();
  }

  @Override
  public void deleteById(Integer id) {
    try {
      lineRouteRepository.deleteById(id);
    } catch (EmptyResultDataAccessException e) {
      throw new NoSuchElementException("Line route with id: " + id + " does not exists!");
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
