package pl.bussystem.domain.busline.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReadBusLineDTO {
  private String name;
  private Integer busStopFromId;
  private Integer busStopToId;
  private Integer driveTime;

}
