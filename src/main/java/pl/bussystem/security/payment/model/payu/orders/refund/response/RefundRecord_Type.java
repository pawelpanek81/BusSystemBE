package pl.bussystem.security.payment.model.payu.orders.refund.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class RefundRecord_Type {
  private String refundId;
  private String extRefundId;
  private String amount;
  private String currencyCode;
  private String description;
  private String creationDateTime;
  private String status;
  private String statusDateTime;
}
