package pl.bussystem.domain.ticket.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.bussystem.domain.ticket.persistence.entity.TicketEntity;

@Repository
interface TicketRepository extends JpaRepository<TicketEntity, Integer> {
}
