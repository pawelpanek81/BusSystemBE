package pl.bussystem.domain.lineinfo.lineroute.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.bussystem.domain.lineinfo.lineroute.persistence.entity.LineRouteEntity;

import java.util.List;

@Repository
public interface LineRouteRepository extends JpaRepository<LineRouteEntity, Integer> {
  List<LineRouteEntity> findAllByOrderBySequence();
}
