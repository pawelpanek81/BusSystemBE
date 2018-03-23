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
public class RemoveBusDTO {
  @NotNull
  @Size(min = 1)
  private String registrationNumber;
}
