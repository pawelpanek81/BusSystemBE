package pl.bussystem.domain.lineinfo.busline.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateBusLineDTO {
  @NotBlank
  private String name;

  @NotNull
  @Digits(integer = 10, fraction = 0)
  private Integer busStopFromId;

  @NotNull
  @Digits(integer = 10, fraction = 0)
  private Integer busStopToId;

  @NotNull
  @Digits(integer = 10, fraction = 0)
  private Integer driveTime;
}
