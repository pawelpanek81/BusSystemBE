package pl.bussystem.domain.lineinfo.busline.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Time;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateScheduleDTO {
  @NotEmpty
  private String code;

  @NotNull
  private Time startHour;

  @NotNull
  private Double driveNettoPrice;
}
