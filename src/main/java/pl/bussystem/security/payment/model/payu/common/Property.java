package pl.bussystem.security.payment.model.payu.common;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Property {
  private String name;
  private String value;
}