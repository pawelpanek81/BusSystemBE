package pl.bussystem.domain.busride.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.bussystem.domain.busride.persistence.entity.BusRideEntity;

import java.util.List;

@Repository
public interface BusRideRepository extends JpaRepository<BusRideEntity, Integer> {
  List<BusRideEntity> findAllByActiveOrderByStartDateTimeAsc(Boolean active);
  List<BusRideEntity> findAllByOrderByStartDateTimeAsc();
}
