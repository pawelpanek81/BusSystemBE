package pl.bussystem.domain.busride.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateBusRideDTO {
  @NotNull
  private LocalDateTime startDateTime;

  @NotNull
  private LocalDateTime endDateTime;

  @NotNull
  private Integer busLine;

  private Integer primaryDriver;

  private Integer secondaryDriver;

  @NotNull
  private Double driveNettoPrice;

  private Integer busId;
}
