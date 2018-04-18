package pl.bussystem.domain.lineinfo.schedule.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.bussystem.domain.lineinfo.schedule.persistence.entity.ScheduleEntity;

import java.util.Optional;

@Repository
public interface ScheduleRepository extends JpaRepository<ScheduleEntity, Integer> {
  Optional<ScheduleEntity> findOneByBusLineIdAndId(Integer busLineId, Integer scheduleId);

}
