package pl.bussystem.security.payment.model.payu.common;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Buyer {
  private String customerId;
  private String extCustomerId;
  private String email;
  private String phone;
  private String firstName;
  private String lastName;
  private String nin;
  private String language;
  private BuyerDelivery delivery;
}
