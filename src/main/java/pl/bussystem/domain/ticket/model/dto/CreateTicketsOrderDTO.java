package pl.bussystem.domain.ticket.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateTicketsOrderDTO {
  @NotBlank
  private String name;

  @NotBlank
  private String surname;

  @NotBlank
  private String email;

  private String phone;

  @NotNull
  private Integer seats;

  @NotNull
  private Integer rideToId;

  @NotNull
  private Integer fromBusStopId;

  @NotNull
  private Integer destinationBusStopId;

  private Integer rideBackId;
}