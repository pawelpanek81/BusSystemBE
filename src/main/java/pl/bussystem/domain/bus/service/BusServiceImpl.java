package pl.bussystem.domain.bus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.bussystem.domain.bus.persistence.entity.BusEntity;
import pl.bussystem.domain.bus.persistence.repository.BusRepository;

import java.util.List;

@Service
public class BusServiceImpl implements BusService {

  private BusRepository busRepository;

  @Autowired
  BusServiceImpl(BusRepository busRepository) {
    this.busRepository = busRepository;
  }

  @Override
  public BusEntity create(BusEntity busEntity) {
    return busRepository.save(busEntity);
  }

  @Override
  public void removeByRegistrationNumber(String registrationNumber) {
    BusEntity busEntity = busRepository.findByRegistrationNumber(registrationNumber);
    if (busEntity != null) {
      busRepository.delete(busEntity);
    }
  }

  @Override
  public Boolean existsByRegistrationNumber(String registrationNumber) {
    return busRepository.existsByRegistrationNumber(registrationNumber);
  }

  @Override
  public List<BusEntity> findAll() {
    return busRepository.findAll();
  }
}
