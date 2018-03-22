package pl.bussystem.domain.busride.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.bussystem.domain.busride.persistence.entity.BusRideEntity;

@Repository
interface BusRideRepository extends JpaRepository<BusRideEntity, Integer> {
}
