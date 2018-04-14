package pl.bussystem.security.payment.model.dto.payload;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BuyerPayload {
  private String email;
  private String phone;
  private String firstName;
  private String lastName;
  private String language;
}
