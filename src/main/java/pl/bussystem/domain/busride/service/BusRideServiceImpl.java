package pl.bussystem.domain.busride.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.bussystem.domain.busride.persistence.entity.BusRideEntity;
import pl.bussystem.domain.busride.persistence.repository.BusRideRepository;

import java.util.List;

@Service
public class BusRideServiceImpl implements BusRideService {
  private BusRideRepository busRideRepository;

  @Autowired
  public BusRideServiceImpl(BusRideRepository busRideRepository) {
    this.busRideRepository = busRideRepository;
  }

  @Override
  public BusRideEntity create(BusRideEntity busRideEntity) {
    return busRideRepository.save(busRideEntity);
  }

  @Override
  public List<BusRideEntity> read() {
    return busRideRepository.findAll();
  }
}