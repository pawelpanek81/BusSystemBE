package pl.bussystem.domain.ticket.service;

import pl.bussystem.domain.ticket.persistence.entity.TicketEntity;

import java.util.List;
import java.util.Optional;

public interface TicketService {
  TicketEntity create(TicketEntity ticketEntity);
  List<TicketEntity> read();
  Optional<TicketEntity> readById(Integer id);
  void makeTicketPaid(Integer ticketId);
}
