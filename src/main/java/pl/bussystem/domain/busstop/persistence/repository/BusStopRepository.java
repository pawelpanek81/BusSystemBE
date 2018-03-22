package pl.bussystem.domain.busstop.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.bussystem.domain.busstop.persistence.entity.BusStopEntity;

@Repository
public interface BusStopRepository extends JpaRepository<BusStopEntity, Integer> {
}
