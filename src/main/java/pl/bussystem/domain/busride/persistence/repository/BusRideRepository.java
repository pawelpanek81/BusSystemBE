package pl.bussystem.domain.busride.persistence.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.bussystem.domain.busride.persistence.entity.BusRideEntity;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BusRideRepository extends JpaRepository<BusRideEntity, Integer> {
  List<BusRideEntity> findAllByActiveOrderByStartDateTimeAsc(Boolean active);
  Page<BusRideEntity> findAllByActiveOrderByStartDateTimeAsc(Pageable pageable, Boolean active);
  Page<BusRideEntity> findAllByOrderByStartDateTimeAsc(Pageable pageable);

  Page<BusRideEntity> findAllByActiveAndStartDateTimeAfterAndStartDateTimeBeforeOrderByStartDateTimeAsc(Pageable pageable,
                                                                                                        Boolean active,
                                                                                                        LocalDateTime after,
                                                                                                        LocalDateTime before);
  Page<BusRideEntity> findAllByStartDateTimeAfterAndStartDateTimeBeforeOrderByStartDateTimeAsc(Pageable pageable,
                                                                                               LocalDateTime after,
                                                                                               LocalDateTime before);
}
