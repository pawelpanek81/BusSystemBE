package pl.bussystem.security.payment.model.payu.orders.notification;

import lombok.*;
import pl.bussystem.security.payment.model.payu.common.Order;
import pl.bussystem.security.payment.model.payu.common.Property;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Notification {
  private Order order;
  private String localReceiptDateTime;
  private List<Property> properties;
}
