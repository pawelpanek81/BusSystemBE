package pl.bussystem.domain.lineroute.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReadLineRouteDTO {
  private Integer id;
  private Integer busLineId;
  private Integer busStopId;
  private Integer sequence;
  private Integer driveTime;
}