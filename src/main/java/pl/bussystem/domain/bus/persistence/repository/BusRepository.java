package pl.bussystem.domain.bus.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.bussystem.domain.bus.persistence.entity.BusEntity;

@Repository
interface BusRepository extends JpaRepository<BusEntity, Integer> {
}
