package pl.bussystem.security.payment.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDTO {
  private TicketDTO fromTicket;
  private TicketDTO toTicket;
  private Integer numberOfPassengers;
  private String signature;
}
