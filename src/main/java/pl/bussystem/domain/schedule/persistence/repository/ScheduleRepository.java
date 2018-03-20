package pl.bussystem.domain.schedule.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.bussystem.domain.schedule.persistence.entity.ScheduleEntity;

@Repository
interface ScheduleRepository extends JpaRepository<ScheduleEntity, Integer> {
}
