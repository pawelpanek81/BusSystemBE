package pl.bussystem.domain.lineinfo.schedule.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import pl.bussystem.domain.lineinfo.busline.service.BusLineService;
import pl.bussystem.domain.lineinfo.schedule.persistence.entity.ScheduleEntity;
import pl.bussystem.domain.lineinfo.schedule.persistence.repository.ScheduleRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class ScheduleServiceImpl implements ScheduleService {
  private ScheduleRepository scheduleRepository;
  private BusLineService busLineService;

  @Autowired
  public ScheduleServiceImpl(ScheduleRepository scheduleRepository,
                             BusLineService busLineService) {
    this.scheduleRepository = scheduleRepository;
    this.busLineService = busLineService;
  }

  @Override
  public ScheduleEntity create(ScheduleEntity scheduleEntity) {
    return scheduleRepository.save(scheduleEntity);
  }

  @Override
  public List<ScheduleEntity> read() {
    return scheduleRepository.findAll();
  }

  @Override
  public void deleteById(Integer id) {
    try {
      scheduleRepository.deleteById(id);
    } catch (EmptyResultDataAccessException e) {
      throw new NoSuchElementException("There is no schedule with: " + id);
    }
  }

  @Override
  public List<ScheduleEntity> readByBusLineId(Integer id) {
    if (busLineService.notExistsById(id)) {
      throw new NoSuchElementException("Bus line with id: " + id + " does not exists!");
    }
    List<ScheduleEntity> allScheduleEntities = this.read();
    return allScheduleEntities.stream()
        .filter(se -> se.getBusLine().getId().equals(id))
        .collect(Collectors.toList());
  }
}
