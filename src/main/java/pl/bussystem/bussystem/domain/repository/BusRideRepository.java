package pl.bussystem.bussystem.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.bussystem.bussystem.domain.entity.BusRideEntity;

@Repository
public interface BusRideRepository extends JpaRepository<BusRideEntity, Integer> {
}
