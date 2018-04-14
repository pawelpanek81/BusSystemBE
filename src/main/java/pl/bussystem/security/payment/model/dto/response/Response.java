package pl.bussystem.security.payment.model.dto.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Response {
  private StatusResponse status;
  private String redirectUri;
  private String orderId;
  private String extOrderId;
}
