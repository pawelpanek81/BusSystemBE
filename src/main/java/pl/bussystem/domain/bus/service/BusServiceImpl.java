package pl.bussystem.domain.bus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.bussystem.domain.bus.persistence.entity.BusEntity;
import pl.bussystem.domain.bus.persistence.repository.BusRepository;

import java.util.List;
import java.util.Optional;

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
  public void removeById(Integer id) {
    Optional<BusEntity> busEntity = busRepository.findById(id);
    busEntity.ifPresent(bus -> busRepository.delete(bus));
  }

  @Override
  public Boolean existsById(Integer id) {
    return busRepository.existsById(id);
  }

  @Override
  public List<BusEntity> findAll() {
    return busRepository.findAll();
  }

  @Override
  public Boolean existsByRegistrationNumber(String registrationNumber) {
    return busRepository.existsByRegistrationNumber(registrationNumber);
  }
}
