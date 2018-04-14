package pl.bussystem.security.payment.model.payu.orders.create.response;

import lombok.*;
import pl.bussystem.security.payment.model.payu.orders.Status;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OrderCreateResponse {
  private String redirectUri;
  private String orderId;
  private String extOrderId;
  private Status status;
}
