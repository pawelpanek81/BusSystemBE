package pl.bussystem.domain.bus.service;

import pl.bussystem.domain.bus.persistence.entity.BusEntity;

public interface BusService {
  BusEntity create(BusEntity busEntity);

  void removeByRegistrationNumber(String registrationNumber);

  Boolean existsByRegistrationNumber(String registrationNumber);
}
