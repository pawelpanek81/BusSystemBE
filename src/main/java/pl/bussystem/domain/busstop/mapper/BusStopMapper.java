package pl.bussystem.domain.busstop.mapper;

import pl.bussystem.domain.busstop.model.dto.CreateBusStopDTO;
import pl.bussystem.domain.busstop.model.dto.ReadBusStopDTO;
import pl.bussystem.domain.busstop.persistence.entity.BusStopEntity;

import java.util.function.Function;

public class BusStopMapper {

  public static Function<? super BusStopEntity, ? extends ReadBusStopDTO> mapToReadBusStopDTO =
      entity -> new ReadBusStopDTO(
        entity.getId(),
        entity.getCity(),
        entity.getName(),
        entity.getLatitude(),
        entity.getLongitude(),
        entity.getAddress());

  public static BusStopEntity mapToBusStopEntity(CreateBusStopDTO dto) {
    return BusStopEntity.builder()
        .city(dto.getCity())
        .name(dto.getName())
        .latitude(dto.getLatitude())
        .longitude(dto.getLongitude())
        .address(dto.getAddress())
        .build();
  }
}
