package pl.bussystem.domain.lineinfo.busline.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.bussystem.domain.busstop.model.dto.ReadBusStopDTO;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReadRouteDTO {
  private Integer id;
  private ReadBusStopDTO busStop;
  private Integer sequence;
  private Integer driveTime;
}
