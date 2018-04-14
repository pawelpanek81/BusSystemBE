package pl.bussystem.security.payment.model.dto.payload;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductPayload {
  private String name;
  private String unitPrice;
  private String quantity;
}
