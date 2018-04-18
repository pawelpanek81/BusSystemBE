package pl.bussystem.domain.lineinfo.lineroute.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.bussystem.domain.lineinfo.lineroute.persistence.entity.LineRouteEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface LineRouteRepository extends JpaRepository<LineRouteEntity, Integer> {
  List<LineRouteEntity> findAllByOrderBySequence();
  Optional<LineRouteEntity> findOneByBusLineIdAndId(Integer busLineId, Integer LineRouteId);
}
