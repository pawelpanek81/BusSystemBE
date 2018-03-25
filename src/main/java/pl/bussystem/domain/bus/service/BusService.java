package pl.bussystem.domain.bus.service;

import pl.bussystem.domain.bus.persistence.entity.BusEntity;

import java.util.List;

public interface BusService {
  BusEntity create(BusEntity busEntity);

  void removeByRegistrationNumber(String registrationNumber);

  Boolean existsByRegistrationNumber(String registrationNumber);

  List<BusEntity> findAll();
}
