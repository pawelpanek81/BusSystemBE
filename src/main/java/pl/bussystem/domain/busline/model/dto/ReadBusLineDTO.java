package pl.bussystem.domain.busline.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import pl.bussystem.domain.busstop.model.dto.ReadBusStopDTO;

@Data
@AllArgsConstructor
public class ReadBusLineDTO {
  private Integer id;
  private String name;
  private ReadBusStopDTO busStopFrom;
  private ReadBusStopDTO busStopTo;
  private Integer driveTime;

}
