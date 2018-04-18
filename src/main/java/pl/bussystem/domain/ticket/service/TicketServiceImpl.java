package pl.bussystem.domain.ticket.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.bussystem.domain.ticket.persistence.entity.TicketEntity;
import pl.bussystem.domain.ticket.persistence.repository.TicketRepository;

import java.util.List;

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
}
