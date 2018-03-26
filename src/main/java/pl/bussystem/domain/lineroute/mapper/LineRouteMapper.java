package pl.bussystem.domain.lineroute.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.bussystem.domain.busline.mapper.BusLineMapper;
import pl.bussystem.domain.busline.persistence.entity.BusLineEntity;
import pl.bussystem.domain.busline.persistence.repository.BusLineRepository;
import pl.bussystem.domain.busstop.mapper.BusStopMapper;
import pl.bussystem.domain.busstop.persistence.entity.BusStopEntity;
import pl.bussystem.domain.busstop.persistence.repository.BusStopRepository;
import pl.bussystem.domain.lineroute.exception.NoSuchBusLineException;
import pl.bussystem.domain.lineroute.exception.NoSuchBusStopException;
import pl.bussystem.domain.lineroute.model.dto.CreateLineRouteDTO;
import pl.bussystem.domain.lineroute.model.dto.ReadLineRouteDTO;
import pl.bussystem.domain.lineroute.persistence.entity.LineRouteEntity;

import java.util.Optional;
import java.util.function.Function;

@Component
public class LineRouteMapper {
  private BusLineRepository busLineRepository;
  private BusStopRepository busStopRepository;

  @Autowired
  public LineRouteMapper(BusLineRepository busLineRepository, BusStopRepository busStopRepository) {
    this.busLineRepository = busLineRepository;
    this.busStopRepository = busStopRepository;
  }

  public static Function<? super LineRouteEntity, ? extends ReadLineRouteDTO> mapToReadLineRouteDTO =
      entity -> new ReadLineRouteDTO(
        entity.getId(),
          BusLineMapper.mapToReadBusLineDTO.apply(entity.getBusLine()),
          BusStopMapper.mapToReadBusStopDTO.apply(entity.getBusStop()),
          entity.getSequence(),
          entity.getDriveTime());

  public LineRouteEntity mapToLineRouteEntity(CreateLineRouteDTO dto) {
    Optional<BusLineEntity> busLine = busLineRepository.findById(dto.getBusLineId());
    Optional<BusStopEntity> busStop = busStopRepository.findById(dto.getBusStopId());

    if (!busLine.isPresent()) {
      throw new NoSuchBusLineException("Bus line with id: " + dto.getBusLineId() + "doesn't exists!");

    }
    if (!busStop.isPresent()) {
      throw new NoSuchBusStopException("Bus stop with id: " + dto.getBusStopId() + "doesn't exists!");
    }

    return LineRouteEntity.builder()
        .busLine(busLine.get())
        .busStop(busStop.get())
        .sequence(dto.getSequence())
        .driveTime(dto.getDriveTime())
        .build();
  }
}
