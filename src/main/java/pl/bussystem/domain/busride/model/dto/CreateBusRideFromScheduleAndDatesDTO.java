package pl.bussystem.domain.busride.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateBusRideFromScheduleAndDatesDTO {
  @NotNull
  private Integer busLine;

  @NotNull
  private LocalDateTime startDateTime;

  @NotNull
  private LocalDateTime endDateTime;

  @NotNull
  private List<Integer> schedulesIds;
}
