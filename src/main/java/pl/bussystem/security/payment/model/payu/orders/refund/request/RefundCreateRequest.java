package pl.bussystem.security.payment.model.payu.orders.refund.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class RefundCreateRequest {
  private String orderId;
  private RefundInfoType refund;
}
