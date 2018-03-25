package pl.bussystem.domain.bus.service;

import pl.bussystem.domain.bus.persistence.entity.BusEntity;

import java.util.List;

public interface BusService {
  BusEntity create(BusEntity busEntity);

  List<BusEntity> read();

  Boolean existsById(Integer id);

  Boolean existsByRegistrationNumber(String registrationNumber);

  void removeById(Integer id);
}
