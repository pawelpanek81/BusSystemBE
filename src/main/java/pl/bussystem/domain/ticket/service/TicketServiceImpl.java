package pl.bussystem.domain.ticket.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pl.bussystem.domain.ticket.persistence.entity.TicketEntity;
import pl.bussystem.domain.ticket.persistence.repository.TicketRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class TicketServiceImpl implements TicketService {
  private static final int FIVE_MINUTES_IN_MILISECONDS = 1000 * 60 * 5;
  private TicketRepository ticketRepository;
  private static final Logger logger = LoggerFactory.getLogger(TicketServiceImpl.class);

  @Autowired
  public TicketServiceImpl(TicketRepository ticketRepository) {
    this.ticketRepository = ticketRepository;
  }

  @Override
  public TicketEntity create(TicketEntity ticketEntity) {
    return ticketRepository.save(ticketEntity);
  }

  @Override
  public List<TicketEntity> read() {
    return ticketRepository.findAll();
  }

  @Override
  public Optional<TicketEntity> readById(Integer id) {
    return ticketRepository.findById(id);
  }

  @Override
  public void makeTicketPaid(Integer ticketId) {
    Optional<TicketEntity> byId = ticketRepository.findById(ticketId);
    TicketEntity ticketEntity = byId.orElseThrow(
        () -> new NoSuchElementException("There is no ticket with id: " + ticketId)
    );
    if (ticketEntity.getPaid()) {
      throw new IllegalArgumentException("Thicket with id: " + ticketId + " is already paid");
    }
    ticketEntity.setPaid(true);
    ticketRepository.save(ticketEntity);
  }

  @Scheduled(fixedRate = FIVE_MINUTES_IN_MILISECONDS)
  public void removeNotPayedTickets() {
    logger.info("Removing not payed tickets " + LocalDateTime.now() + "...");
    List<TicketEntity> tickets = ticketRepository.findAllByPaid(Boolean.FALSE);

    for (TicketEntity ticket : tickets) {
      if (LocalDateTime.now().isAfter(ticket.getDateTime().plusHours(1))) {
        ticketRepository.delete(ticket);
      }
    }
  }
}