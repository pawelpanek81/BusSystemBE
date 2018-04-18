package pl.bussystem.domain.lineinfo.schedule.service;

import pl.bussystem.domain.lineinfo.schedule.persistence.entity.ScheduleEntity;

import java.util.List;

public interface ScheduleService {
  ScheduleEntity create(ScheduleEntity scheduleEntity);

  List<ScheduleEntity> read();

  void deleteById(Integer id);

  List<ScheduleEntity> readByBusLineId(Integer id);

  void deleteByBusLineIdAndScheduleId(Integer busLineID, Integer scheduleId);
}
