package pl.bussystem.domain.ticket.service;

import pl.bussystem.domain.ticket.exception.NoSuchTicketException;
import pl.bussystem.domain.ticket.exception.QRCodeGenerationFailedException;
import pl.bussystem.domain.ticket.persistence.entity.TicketEntity;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

public interface TicketService {
  TicketEntity create(TicketEntity ticketEntity);
  List<TicketEntity> read();
  List<TicketEntity> readUserTickets(Principal principal);
  Optional<TicketEntity> readById(Integer id);
  void makeTicketPaid(Integer ticketId);
  byte[] generateQRCode(Integer ticketId) throws NoSuchTicketException, QRCodeGenerationFailedException;
  boolean verifyTicket(String owner, String route, Integer ticketId,
                       String paymentStatus, String payload) throws NoSuchTicketException;
}
