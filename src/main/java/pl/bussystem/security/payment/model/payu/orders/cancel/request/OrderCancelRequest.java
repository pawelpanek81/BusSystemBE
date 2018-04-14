package pl.bussystem.security.payment.model.payu.orders.cancel.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OrderCancelRequest {
  private String orderId;
}
