package pl.bussystem.security.payment.model.payu.common;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PayMethod {
  private String type;
  private String value;
  private String authorizationCode;
}
