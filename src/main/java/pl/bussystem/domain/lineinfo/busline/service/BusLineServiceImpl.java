package pl.bussystem.domain.lineinfo.busline.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import pl.bussystem.domain.lineinfo.busline.persistence.entity.BusLineEntity;
import pl.bussystem.domain.lineinfo.busline.persistence.repository.BusLineRepository;

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
  @CacheEvict("busStops")
  public BusLineEntity create(BusLineEntity busLineEntity) {
    return busLineRepository.save(busLineEntity);
  }

  @Override
  public List<BusLineEntity> read() {
    return busLineRepository.findAll();
  }

  @Override
  @CacheEvict("busStops")
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
