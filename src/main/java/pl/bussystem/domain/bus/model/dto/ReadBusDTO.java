package pl.bussystem.domain.bus.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReadBusDTO {
  private Integer id;
  private String registrationNumber;
  private String brand;
  private String model;
  private Integer seats;
}