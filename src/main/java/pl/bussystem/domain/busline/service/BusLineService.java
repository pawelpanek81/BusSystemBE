package pl.bussystem.domain.busline.service;

import pl.bussystem.domain.busline.persistence.entity.BusLineEntity;

import java.util.List;

public interface BusLineService {
  BusLineEntity create(BusLineEntity busLineEntity);

  List<BusLineEntity> read();

  void deleteById(Integer id);
}
