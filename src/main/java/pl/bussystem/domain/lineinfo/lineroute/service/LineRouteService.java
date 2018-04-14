package pl.bussystem.domain.lineinfo.lineroute.service;

import pl.bussystem.domain.lineinfo.lineroute.persistence.entity.LineRouteEntity;

import java.util.List;

public interface LineRouteService {
  LineRouteEntity create(LineRouteEntity lineRouteEntity);

  List<LineRouteEntity> read();

  void deleteByBusLineIdAndLineRouteId(Integer busLineId, Integer lineRouteId);

  List<LineRouteEntity> readByBusLineId(Integer id);

  LineRouteEntity readById(Integer id);
}
