package pl.bussystem.security.payment.model.payu.common;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BuyerDelivery {
  private String street;
  private String postalBox;
  private String postalCode;
  private String city;
  private String state;
  private String countryCode;
  private String name;
  private String recipientName;
  private String recipientEmail;
  private String recipientPhone;
}
