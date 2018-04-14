package pl.bussystem.security.payment.model.payu.orders.create.request;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Settings {
  String invoiceDisabled;
}
