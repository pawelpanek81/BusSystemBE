package pl.bussystem.domain.busline.busline.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import pl.bussystem.domain.busline.busline.persistence.entity.BusLineEntity;
import pl.bussystem.domain.busline.busline.persistence.repository.BusLineRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class BusLineServiceImpl implements BusLineService {
  private BusLineRepository busLineRepository;

  @Autowired
  public BusLineServiceImpl(BusLineRepository busLineRepository) {
    this.busLineRepository = busLineRepository;
  }

  @Override
  public BusLineEntity create(BusLineEntity busLineEntity) {
    return busLineRepository.save(busLineEntity);
  }

  @Override
  public List<BusLineEntity> read() {
    return busLineRepository.findAll();
  }

  @Override
  public void deleteById(Integer id) {
    try {
      busLineRepository.deleteById(id);
    } catch (EmptyResultDataAccessException e) {
      throw new NoSuchElementException("Bus line with id: " + id + " does not exists!");
    }
  }

  @Override
  public BusLineEntity readById(Integer id) {
    return busLineRepository
        .findById(id)
        .orElseThrow(() -> new NoSuchElementException("Bus line with id: " + id + " does not exists!"));
  }

  @Override
  public boolean notExistsById(Integer id) {
    return !busLineRepository.existsById(id);
  }
}
