package pl.bussystem.domain.bus.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BusAddDTO {
  @NotNull
  @Size(min = 4, max = 8)
  private String registrationNumber;

  @NotNull
  private String brand;

  @NotNull
  private String model;
}
