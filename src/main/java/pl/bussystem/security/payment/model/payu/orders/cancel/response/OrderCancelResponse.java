package pl.bussystem.security.payment.model.payu.orders.cancel.response;

import lombok.*;
import pl.bussystem.security.payment.model.payu.common.Status;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OrderCancelResponse {
  private String orderId;
  private String extOrderId;
  private Status status;
}
