package pl.bussystem.domain.busstop.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddBusStopDTO {
  @NotNull
  @Size(min = 1)
  private String city;

  @NotNull
  @Size(min = 1)
  private String name;
}