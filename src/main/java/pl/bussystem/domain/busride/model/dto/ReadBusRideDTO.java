package pl.bussystem.domain.busride.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.bussystem.domain.bus.model.dto.ReadBusDTO;
import pl.bussystem.domain.lineinfo.busline.model.dto.ReadBusLineDTO;
import pl.bussystem.domain.user.model.dto.ReadUserDTO;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReadBusRideDTO {
  private Integer id;
  private LocalDateTime startDateTime;
  private LocalDateTime endDateTime;
  private ReadBusLineDTO busLine;
  private ReadUserDTO primaryDriver;
  private ReadUserDTO secondaryDriver;
  private Double driveNettoPrice;
  private ReadBusDTO bus;
  private Boolean active;
}
