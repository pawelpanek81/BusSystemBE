package pl.bussystem.security.payment.model.dto.payload;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SettingsPayload {
  String invoiceDisabled;
}
