package pl.bussystem.security.payment.model.payu.orders;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {
  private String name;
  private String unitPrice;
  private String quantity;
  private String virtual;
  private String listingDate;
}
