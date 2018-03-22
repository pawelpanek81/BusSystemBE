package pl.bussystem.domain.lineroute.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.bussystem.domain.lineroute.persistence.entity.LineRouteEntity;

@Repository
interface LineRouteRepository extends JpaRepository<LineRouteEntity, Integer> {
}
