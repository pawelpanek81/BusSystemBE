package pl.bussystem.security.payment.model.payu.orders.create.request;

import lombok.*;
import pl.bussystem.security.payment.model.payu.orders.Buyer;
import pl.bussystem.security.payment.model.payu.orders.PayMethod;
import pl.bussystem.security.payment.model.payu.orders.Product;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreateRequest {
  private String extOrderId;
  private String notifyUrl;
  private String customerIp;
  private String merchantPosId;
  private String validityTime;
  private String description;
  private String additionalDescription;
  private String currencyCode;
  private String totalAmount;
  private String continueUrl;
  private Settings settings;
  private Buyer buyer;
  private List<Product> products;
  private PayMethod payMethods;
}
