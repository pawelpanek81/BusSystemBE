package pl.bussystem.domain.ticket.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.bussystem.domain.ticket.persistence.entity.TicketEntity;
import pl.bussystem.domain.ticket.persistence.repository.TicketRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class TicketServiceImpl implements TicketService {
  private TicketRepository ticketRepository;

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
}