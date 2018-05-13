package pl.bussystem.domain.lineinfo.busline.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReadScheduleDTO {
  private Integer id;
  private String code;
  private Time startHour;
  private Double driveNettoPrice;
  private Boolean enabled;
}
