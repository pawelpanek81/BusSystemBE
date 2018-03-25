package pl.bussystem.domain.busstop.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusStopDTO {
  @NotBlank
  private String city;

  @NotBlank
  private String name;

  @NotBlank
  private String latitude;

  @NotBlank
  private String longitude;

  private String address;
}