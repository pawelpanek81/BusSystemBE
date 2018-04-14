package pl.bussystem.security.payment.model.dto.payload;

import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Payload {
  private String notifyUrl;
  private String customerIp;
  private String merchantPosId;
  private String description;
  private String currencyCode;
  private String totalAmount;
  private BuyerPayload buyer;
  private SettingsPayload settings;
  private List<ProductPayload> products;
}
