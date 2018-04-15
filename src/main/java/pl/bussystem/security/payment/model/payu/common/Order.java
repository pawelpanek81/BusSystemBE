package pl.bussystem.security.payment.model.payu.common;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Order {
  private String orderId;
  private String extOrderId;
  private String orderCreateDate;
  private String notifyUrl;
  private String orderUrl;
  private String customerIp;
  private String merchantPosId;
  private String validityTime;
  private String description;
  private String additionalDescription;
  private String currencyCode;
  private String totalAmount;
  private String status;
  private Buyer buyer;
  private List<Product> products;
}
