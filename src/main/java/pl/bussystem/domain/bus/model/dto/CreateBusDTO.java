package pl.bussystem.domain.bus.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
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
