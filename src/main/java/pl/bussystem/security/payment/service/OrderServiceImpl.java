package pl.bussystem.security.payment.service;

import org.springframework.stereotype.Service;
import pl.bussystem.domain.ticket.persistence.repository.TicketRepository;
import pl.bussystem.domain.user.persistence.entity.AccountEntity;
import pl.bussystem.security.payment.persistence.entity.OrderEntity;
import pl.bussystem.security.payment.persistence.repository.OrderRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

  private OrderRepository orderRepository;
  private TicketRepository ticketRepository;

  public OrderServiceImpl(OrderRepository orderRepository,
                          TicketRepository ticketRepository) {
    this.orderRepository = orderRepository;
    this.ticketRepository = ticketRepository;
  }

  @Override
  public List<OrderEntity> readByAccountEntity(AccountEntity accountEntity) {
    return orderRepository.findAll().stream()
        .filter(orderEntity -> ticketRepository
            .findById(Integer.parseInt(orderEntity.getOrderId().split(",")[0]))
            .filter(ticketEntity -> ticketEntity.getUserAccount() != null)
            .map(ticketEntity -> ticketEntity.getUserAccount().equals(accountEntity))
            .orElse(false))
        .collect(Collectors.toList());
  }
}
