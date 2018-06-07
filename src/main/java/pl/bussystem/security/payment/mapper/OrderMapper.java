package pl.bussystem.security.payment.mapper;

import org.springframework.stereotype.Component;
import pl.bussystem.domain.ticket.mapper.TicketMapper;
import pl.bussystem.security.payment.model.dto.ReadOrderDTO;
import pl.bussystem.security.payment.persistence.entity.OrderEntity;

@Component
public class OrderMapper {

  public ReadOrderDTO mapToReadOrderDTO(OrderEntity orderEntity) {
    String[] orderIdComponents = orderEntity.getOrderId().split(",");
    String orderId = orderIdComponents[0];
    if (orderIdComponents.length > 2) {
      orderId += "-" + orderIdComponents[1];
    }

    return new ReadOrderDTO(
        orderId,
        orderEntity.getURL(),
        TicketMapper.mapToReadTicketDTO.apply(orderEntity.getFirstTicket()),
        orderEntity.getSecondTicket() != null ? TicketMapper.mapToReadTicketDTO.apply(orderEntity.getSecondTicket()) : null
    );
  }
}
