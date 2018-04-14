package pl.bussystem.security.payment.model.payu.orders.update.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OrderStatusUpdateRequest {
  private String orderId;
  private String orderStatus;
}
