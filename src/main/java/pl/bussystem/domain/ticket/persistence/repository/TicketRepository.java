package pl.bussystem.domain.ticket.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.bussystem.domain.busride.persistence.entity.BusRideEntity;
import pl.bussystem.domain.ticket.persistence.entity.TicketEntity;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<TicketEntity, Integer> {
  List<TicketEntity> findByBusRide(BusRideEntity busRideEntity);
  List<TicketEntity> findAllByPaid(Boolean paid);
}
