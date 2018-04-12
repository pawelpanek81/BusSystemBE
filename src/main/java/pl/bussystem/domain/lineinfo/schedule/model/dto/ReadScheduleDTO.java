package pl.bussystem.domain.lineinfo.schedule.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.bussystem.domain.lineinfo.busline.model.dto.ReadBusLineDTO;

import java.sql.Time;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReadScheduleDTO {
  private Integer id;
  private ReadBusLineDTO busLine;
  private String code;
  private Time startHour;
  private Boolean enabled;
}
