package pl.bussystem.domain.ticket.service;

import pl.bussystem.domain.ticket.persistence.entity.TicketEntity;

import java.util.List;

public interface TicketService {
  TicketEntity create(TicketEntity ticketEntity);
  List<TicketEntity> read();
}
