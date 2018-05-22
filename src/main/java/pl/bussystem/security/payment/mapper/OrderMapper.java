package pl.bussystem.security.payment.mapper;

import pl.bussystem.security.payment.model.dto.ReadOrderDTO;
import pl.bussystem.security.payment.persistence.entity.OrderEntity;


public class OrderMapper {

  public static ReadOrderDTO mapToReadOrderDTO(OrderEntity orderEntity) {
    String[] orderIdComponents = orderEntity.getOrderId().split(",");
    String orderId = orderIdComponents[0] + ",";
    if (orderIdComponents.length > 2) {
      orderId += orderIdComponents[1];
    }

    return new ReadOrderDTO(orderId, orderEntity.getURL());
  }
}
