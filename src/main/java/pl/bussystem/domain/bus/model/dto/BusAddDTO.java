package pl.bussystem.domain.bus.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BusAddDTO {
  @NotBlank
  private String registrationNumber;

  @NotBlank
  private String brand;

  @NotBlank
  private String model;

  @NotNull
  @Min(1)
  private Integer seats;
}
