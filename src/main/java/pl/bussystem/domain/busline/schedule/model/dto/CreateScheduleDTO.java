package pl.bussystem.domain.busline.schedule.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Time;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateScheduleDTO {
  @NotNull
  @Digits(integer = 10, fraction = 0)
  private Integer busLineId;

  @NotBlank
  private String code;

  @NotNull
  private Time startHour;

  @NotNull
  private Boolean enabled;
}
