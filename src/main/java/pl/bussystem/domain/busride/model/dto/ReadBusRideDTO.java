package pl.bussystem.domain.busride.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReadBusRideDTO {
  private Integer id;
  private LocalDateTime startDateTime;
  private LocalDateTime endDateTime;
  private Integer busLine;
}
