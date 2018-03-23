package pl.bussystem.domain.bus.service;

import pl.bussystem.domain.bus.persistence.entity.BusEntity;

public interface BusService {
  BusEntity create(BusEntity busEntity);
  Boolean existsByRegistrationNumber(String registrationNumber);
}
