package pl.bussystem.domain.bus.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateBusDTO {
  @NotBlank
  private String registrationNumber;

  @NotBlank
  private String brand;

  @NotBlank
  private String model;

  @NotNull
  @Digits(integer = 10, fraction = 0)
  private Integer seats;
}
