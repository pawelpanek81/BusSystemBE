package pl.bussystem.security.payment.model.payu.oauth.authorization;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Response {
  private String access_token;
  private String token_type;
  private Integer expires_in;
  private String grant_type;
}
