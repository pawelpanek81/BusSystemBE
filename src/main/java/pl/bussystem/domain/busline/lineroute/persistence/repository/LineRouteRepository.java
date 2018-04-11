package pl.bussystem.domain.busline.lineroute.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.bussystem.domain.busline.lineroute.persistence.entity.LineRouteEntity;

@Repository
public interface LineRouteRepository extends JpaRepository<LineRouteEntity, Integer> {
}
