package pl.bussystem.domain.busride.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.bussystem.domain.busstop.model.dto.ReadBusStopDTO;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusJourneySearchDTO {
  private List<BusTripSearchDTO> departurePossibilities;
  private List<BusTripSearchDTO> returnPossibilities;
  private ReadBusStopDTO stopFrom;
  private ReadBusStopDTO stopTo;
}
