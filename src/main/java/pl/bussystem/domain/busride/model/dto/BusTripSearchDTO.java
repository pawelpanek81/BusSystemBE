package pl.bussystem.domain.busride.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusTripSearchDTO {
  private Integer rideId;
  private Integer busLineId;
  private LocalDateTime startDateTime;
  private LocalDateTime endDateTime;
  private Double price;
}
