package pl.bussystem.security.payment.model.payu.common;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Status {
  private String statusCode;
  private String statusDesc;
}
