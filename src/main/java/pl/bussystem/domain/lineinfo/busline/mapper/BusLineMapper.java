package pl.bussystem.domain.lineinfo.busline.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.bussystem.domain.lineinfo.busline.exception.NoSuchBusStopFromException;
import pl.bussystem.domain.lineinfo.busline.exception.NoSuchBusStopToException;
import pl.bussystem.domain.lineinfo.busline.model.dto.CreateBusLineDTO;
import pl.bussystem.domain.lineinfo.busline.model.dto.ReadBusLineDTO;
import pl.bussystem.domain.lineinfo.busline.persistence.entity.BusLineEntity;
import pl.bussystem.domain.busstop.mapper.BusStopMapper;
import pl.bussystem.domain.busstop.persistence.entity.BusStopEntity;
import pl.bussystem.domain.busstop.persistence.repository.BusStopRepository;

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
      entity -> new ReadBusLineDTO(
          entity.getId(),
          entity.getName(),
          BusStopMapper.mapToReadBusStopDTO.apply(entity.getFrom()),
          BusStopMapper.mapToReadBusStopDTO.apply(entity.getTo()),
          entity.getDriveTime());

  public BusLineEntity mapToBusLineEntity(CreateBusLineDTO dto) {
    Optional<BusStopEntity> busStopFrom = busStopRepository.findById(dto.getBusStopFromId());
    Optional<BusStopEntity> busStopTo = busStopRepository.findById(dto.getBusStopToId());

    if (!busStopFrom.isPresent()) {
      throw new NoSuchBusStopFromException("Bus stop with id: " + dto.getBusStopFromId() + "doesn't exists!");
    }
    if (!busStopTo.isPresent()) {
      throw new NoSuchBusStopToException("Bus stop with id: " + dto.getBusStopToId() + "doesn't exists!");
    }

    return BusLineEntity.builder()
        .name(dto.getName())
        .from(busStopFrom.get())
        .to(busStopTo.get())
        .driveTime(dto.getDriveTime())
        .build();
  }

}