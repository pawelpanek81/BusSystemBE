package pl.bussystem.domain.busride.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusJourneySearchDTO {
  private List<ReadBusRideDTO> departurePossibilites;
  private List<ReadBusRideDTO> returnPossibilites;
}
