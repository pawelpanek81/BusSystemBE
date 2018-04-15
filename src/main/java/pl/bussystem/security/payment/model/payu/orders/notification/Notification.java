package pl.bussystem.security.payment.model.payu.orders.notification;

import lombok.*;
import pl.bussystem.security.payment.model.payu.common.Order;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Notification {
  private Order order;
}
