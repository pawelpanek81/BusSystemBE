package pl.bussystem.domain.lineroute.service;

import pl.bussystem.domain.lineroute.persistence.entity.LineRouteEntity;

import java.util.List;

public interface LineRouteService {
  LineRouteEntity create(LineRouteEntity lineRouteEntity);

  List<LineRouteEntity> read();

  void deleteById(Integer id);

  List<LineRouteEntity> readByBusLineId(Integer id);

  LineRouteEntity readById(Integer id);
}
