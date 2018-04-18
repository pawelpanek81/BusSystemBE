package pl.bussystem.domain.ticket.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateTicketDTO {
  private Integer userAccountId;

  @NotBlank
  private String name;

  @NotBlank
  private String surname;

  @NotBlank
  private String email;

  private String phone;

  @NotNull
  private Double price;

  @NotNull
  private Integer busRideId;
}