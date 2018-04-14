package pl.bussystem.security.payment.model.payu.orders.refund.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class RefundInfoType {
  private String description;
  private String amount;
  private String extRefundId;
  private String bankDescription;
  private String type;
}
