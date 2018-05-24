package pl.bussystem.domain.ticket.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pl.bussystem.domain.ticket.persistence.entity.TicketEntity;
import pl.bussystem.domain.ticket.persistence.repository.TicketRepository;
import pl.bussystem.domain.user.service.AccountService;
import pl.bussystem.security.payment.persistence.entity.OrderEntity;
import pl.bussystem.security.payment.persistence.repository.OrderRepository;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TicketServiceImpl implements TicketService {
  private static final int FIVE_MINUTES_IN_MILLISECONDS = 1000 * 60 * 5;
  private TicketRepository ticketRepository;
  private OrderRepository orderRepository;
  private AccountService accountService;
  private static final Logger logger = LoggerFactory.getLogger(TicketServiceImpl.class);

  @Autowired
  public TicketServiceImpl(TicketRepository ticketRepository,
                           OrderRepository orderRepository,
                           AccountService accountService) {
    this.ticketRepository = ticketRepository;
    this.orderRepository = orderRepository;
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
  public List<TicketEntity> readUserTickets(Principal principal) {
    return this.read().stream()
        .filter(ticketEntity -> ticketEntity.getUserAccount() != null)
        .filter(ticketEntity -> ticketEntity.getUserAccount().equals(accountService.findAccountByPrincipal(principal)))
        .filter(ticketEntity -> ticketEntity.getPaid().equals(true))
        .collect(Collectors.toList());
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

  @Scheduled(fixedRate = FIVE_MINUTES_IN_MILLISECONDS)
  public void removeNotPayedTickets() {
    logger.info("Removing not payed tickets " + LocalDateTime.now() + "...");
    List<TicketEntity> tickets = ticketRepository.findAllByPaid(Boolean.FALSE);
    List<OrderEntity> orders = orderRepository.findAll();

    for (TicketEntity ticket : tickets) {
      if (LocalDateTime.now().isAfter(ticket.getDateTime().plusHours(1))) {
        ticketRepository.delete(ticket);

        logger.info("Removing not payed orders " + LocalDateTime.now() + "...");
        for (OrderEntity order : orders) {
          String[] splittedId = order.getOrderId().split(",");
          if (splittedId[0].equals(ticket.getId().toString())) {
            orderRepository.delete(order);
          }
        }
      }
    }
  }
}