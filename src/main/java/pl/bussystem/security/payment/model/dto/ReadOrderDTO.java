package pl.bussystem.security.payment.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.bussystem.domain.ticket.model.dto.ReadAvailableTicketsDTO;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReadOrderDTO {
  private String orderId;
  private String URL;
  private ReadAvailableTicketsDTO firstTicket;
  private ReadAvailableTicketsDTO secondTicket;
}
