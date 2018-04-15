package pl.bussystem.security.payment.model.payu.orders.refund.response;

import lombok.*;
import pl.bussystem.security.payment.model.payu.common.Status;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class RefundCreateResponse {
  private String orderId;
  private RefundRecord_Type refund;
  private Status status;
}
