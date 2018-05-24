package pl.bussystem.security.payment.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.bussystem.domain.ticket.mapper.TicketMapper;
import pl.bussystem.domain.ticket.persistence.entity.TicketEntity;
import pl.bussystem.domain.ticket.persistence.repository.TicketRepository;
import pl.bussystem.security.payment.model.dto.ReadOrderDTO;
import pl.bussystem.security.payment.persistence.entity.OrderEntity;

import java.util.Optional;

@Component
public class OrderMapper {
  private TicketRepository ticketRepository;

  @Autowired
  public OrderMapper(TicketRepository ticketRepository) {
    this.ticketRepository = ticketRepository;
  }

  public ReadOrderDTO mapToReadOrderDTO(OrderEntity orderEntity) {
    String[] orderIdComponents = orderEntity.getOrderId().split(",");
    String orderId = orderIdComponents[0] + ",";
    if (orderIdComponents.length > 2) {
      orderId += orderIdComponents[1];
    }

    Optional<TicketEntity> firstTicket = ticketRepository.findById(Integer.valueOf(orderIdComponents[0]));
    Optional<TicketEntity> secondTicket = Optional.empty();
    if (orderIdComponents.length == 3) {
      secondTicket = ticketRepository.findById(Integer.valueOf(orderIdComponents[1]));
    }

    return new ReadOrderDTO(
        orderId,
        orderEntity.getURL(),
        firstTicket.map(ticketEntity -> TicketMapper.mapToReadTicketDTO.apply(ticketEntity)).orElse(null),
        secondTicket.map(ticketEntity -> TicketMapper.mapToReadTicketDTO.apply(ticketEntity)).orElse(null)
    );
  }
}
