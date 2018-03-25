package pl.bussystem.domain.bus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import pl.bussystem.domain.bus.exception.NoSuchBusException;
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
  public void removeById(Integer id) {
    try {
      busRepository.deleteById(id);
    } catch (EmptyResultDataAccessException e) {
      throw new NoSuchBusException("Bus with id: " + id + " does not exists!");
    }
  }

  @Override
  public Boolean existsById(Integer id) {
    return busRepository.existsById(id);
  }

  @Override
  public List<BusEntity> read() {
    return busRepository.findAll();
  }

  @Override
  public Boolean existsByRegistrationNumber(String registrationNumber) {
    return busRepository.existsByRegistrationNumber(registrationNumber);
  }
}
