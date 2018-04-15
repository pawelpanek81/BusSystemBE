package pl.bussystem.security.payment.model.payu.orders.retrieve.response;

import lombok.*;
import pl.bussystem.security.payment.model.payu.common.Order;
import pl.bussystem.security.payment.model.payu.common.Status;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OrderRetrieveResponse {
  private Order order;
  private Status status;
}
