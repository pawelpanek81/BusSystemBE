package pl.bussystem.security.payment.model.payu.orders.retrieve.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OrderRetrieveRequest {
  private String orderId;
}
