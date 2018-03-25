package pl.bussystem.domain.busline.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.bussystem.domain.busline.model.CreateBusLineDTO;
import pl.bussystem.domain.busline.model.ReadBusLineDTO;
import pl.bussystem.domain.busline.persistence.entity.BusLineEntity;
import pl.bussystem.domain.busstop.persistence.entity.BusStopEntity;
import pl.bussystem.domain.busstop.persistence.repository.BusStopRepository;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Function;

@Component
public class BusLineMapper {
  private BusStopRepository busStopRepository;

  @Autowired
  public BusLineMapper(BusStopRepository busStopRepository) {
    this.busStopRepository = busStopRepository;
  }

  public static Function<? super BusLineEntity, ? extends ReadBusLineDTO> mapToReadBusLineDTO =
      busLineEntity -> new ReadBusLineDTO(
          busLineEntity.getName(),
          busLineEntity.getFrom().getId(),
          busLineEntity.getTo().getId(),
          busLineEntity.getDriveTime());

  public BusLineEntity mapToBusLineEntity(CreateBusLineDTO dto) {
    Optional<BusStopEntity> busStopFrom = busStopRepository.findById(dto.getBusStopFromId());
    Optional<BusStopEntity> busStopTo = busStopRepository.findById(dto.getBusStopToId());

    if (!busStopFrom.isPresent()) {
      throw new NoSuchElementException("Bus Stop with id: " + dto.getBusStopFromId() + "doesn't exists!");
    }
    if (!busStopTo.isPresent()) {
      throw new NoSuchElementException("Bus Stop with id: " + dto.getBusStopToId() + "doesn't exists!");
    }

    return BusLineEntity.builder()
        .name(dto.getName())
        .from(busStopFrom.get())
        .to(busStopTo.get())
        .driveTime(dto.getDriveTime())
        .build();
  }
}