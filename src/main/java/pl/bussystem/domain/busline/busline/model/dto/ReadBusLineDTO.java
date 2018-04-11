package pl.bussystem.domain.busline.busline.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import pl.bussystem.domain.busstop.model.dto.ReadBusStopDTO;

@Data
@AllArgsConstructor
public class ReadBusLineDTO {
  private Integer id;
  private String name;
  private ReadBusStopDTO from;
  private ReadBusStopDTO to;
  private Integer driveTime;

}
