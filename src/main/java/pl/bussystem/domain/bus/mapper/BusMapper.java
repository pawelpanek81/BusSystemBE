package pl.bussystem.domain.bus.mapper;

import pl.bussystem.domain.bus.model.dto.CreateBusDTO;
import pl.bussystem.domain.bus.model.dto.ReadBusDTO;
import pl.bussystem.domain.bus.persistence.entity.BusEntity;

import java.util.function.Function;

public class BusMapper {
  public static Function<? super BusEntity, ? extends ReadBusDTO> mapToReadBusDTO =
      entity -> new ReadBusDTO(
          entity.getId(),
          entity.getRegistrationNumber(),
          entity.getBrand(),
          entity.getModel(),
          entity.getSeats()
      );

  public static BusEntity mapToBusEntity(CreateBusDTO dto) {
    return BusEntity.builder()
        .registrationNumber(dto.getRegistrationNumber())
        .brand(dto.getBrand())
        .model(dto.getModel())
        .seats(dto.getSeats())
        .build();
  }
}