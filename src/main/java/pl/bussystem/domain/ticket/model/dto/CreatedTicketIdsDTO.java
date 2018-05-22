package pl.bussystem.domain.ticket.model.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatedTicketIdsDTO {
  private Integer toTicket;
  private Integer backTicket;
}
