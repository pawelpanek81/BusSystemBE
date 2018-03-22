package pl.bussystem.domain.busstop.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddBusStopDTO {
  private String city;
  private String name;
}