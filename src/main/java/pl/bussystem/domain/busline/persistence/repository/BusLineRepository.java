package pl.bussystem.domain.busline.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.bussystem.domain.busline.persistence.entity.BusLineEntity;

@Repository
public interface BusLineRepository extends JpaRepository<BusLineEntity, Integer> {
}
