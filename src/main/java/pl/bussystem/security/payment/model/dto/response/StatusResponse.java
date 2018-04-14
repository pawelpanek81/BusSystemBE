package pl.bussystem.security.payment.model.dto.response;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StatusResponse {
  private String statusCode;
}
