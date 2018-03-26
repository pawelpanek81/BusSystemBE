package pl.bussystem.domain.busstop.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReadBusStopDTO {
  private Integer id;
  private String city;
  private String name;
  private String latitude;
  private String longitude;
  private String address;
}
