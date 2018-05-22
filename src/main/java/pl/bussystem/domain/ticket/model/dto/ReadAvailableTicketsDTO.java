package pl.bussystem.domain.ticket.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.bussystem.domain.busride.model.dto.ReadBusRideDTO;
import pl.bussystem.domain.user.model.dto.ReadUserDTO;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReadAvailableTicketsDTO {
  private Integer id;
  private ReadUserDTO userAccount;
  private String name;
  private String surname;
  private String email;
  private String phone;
  private LocalDateTime dateTime;
  private Double price;
  private ReadBusRideDTO busRide;
  private Boolean paid;
}