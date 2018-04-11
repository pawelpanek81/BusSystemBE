package pl.bussystem.domain.schedule.service;

import pl.bussystem.domain.schedule.persistence.entity.ScheduleEntity;

import java.util.List;

public interface ScheduleService {
  ScheduleEntity create(ScheduleEntity scheduleEntity);

  List<ScheduleEntity> read();

  void deleteById(Integer id);

  List<ScheduleEntity> readByBusLineId(Integer id);
}
