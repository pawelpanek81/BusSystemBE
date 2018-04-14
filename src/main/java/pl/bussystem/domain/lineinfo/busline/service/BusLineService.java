package pl.bussystem.domain.lineinfo.busline.service;

import pl.bussystem.domain.lineinfo.busline.persistence.entity.BusLineEntity;

import java.util.List;

public interface BusLineService {
  BusLineEntity create(BusLineEntity busLineEntity);

  List<BusLineEntity> read();

  void deleteById(Integer id);

  BusLineEntity readById(Integer id);

  boolean notExistsById(Integer id);
}
