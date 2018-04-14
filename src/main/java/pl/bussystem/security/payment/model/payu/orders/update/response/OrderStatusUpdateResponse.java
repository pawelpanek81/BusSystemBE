package pl.bussystem.security.payment.model.payu.orders.update.response;

import lombok.*;
import pl.bussystem.security.payment.model.payu.orders.Status;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OrderStatusUpdateResponse {
  private Status status;
}
