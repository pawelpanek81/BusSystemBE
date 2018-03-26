package pl.bussystem.domain.lineroute.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.bussystem.domain.busline.model.dto.ReadBusLineDTO;
import pl.bussystem.domain.busstop.model.dto.ReadBusStopDTO;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReadLineRouteDTO {
  private Integer id;
  private ReadBusLineDTO busLine;
  private ReadBusStopDTO busStop;
  private Integer sequence;
  private Integer driveTime;
}