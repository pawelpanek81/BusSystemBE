package pl.bussystem.domain.lineinfo.busline.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateRouteDTO {
  @NotNull
  @Digits(integer = 10, fraction = 0)
  private Integer busStopId;

  @NotNull
  @Digits(integer = 10, fraction = 0)
  private Integer sequence;

  @NotNull
  @Digits(integer = 10, fraction = 0)
  private Integer driveTime;
}
