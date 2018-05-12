package pl.bussystem.domain.busride.persistence.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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

  @Query("select br from BusRideEntity br where " +
      "    (?1 is null or br.active = ?1) " +
      "and (cast(?2 as timestamp) is null or br.startDateTime > ?2) " +
      "and (cast(?3 as timestamp) is null or br.endDateTime < ?3) " +
      "and (?4 is null or br.busLine = ?4)")
  Page<BusRideEntity> findAllByQuery(Pageable pageable,
                                     Boolean active,
                                     LocalDateTime after,
                                     LocalDateTime before,
                                     Integer lineId);
}
