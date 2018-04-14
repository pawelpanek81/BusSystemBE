package pl.bussystem.domain.lineinfo.busline.mapper;

import pl.bussystem.domain.busstop.mapper.BusStopMapper;
import pl.bussystem.domain.lineinfo.busline.model.dto.ReadRouteDTO;
import pl.bussystem.domain.lineinfo.lineroute.persistence.entity.LineRouteEntity;

import java.util.function.Function;

public class RouteMapper {

  public static Function<? super LineRouteEntity, ? extends ReadRouteDTO> mapToReadLineRouteDTO =
      entity -> new ReadRouteDTO(
          entity.getId(),
          BusStopMapper.mapToReadBusStopDTO.apply(entity.getBusStop()),
          entity.getSequence(),
          entity.getDriveTime());
}
