package pl.bussystem.domain.ticket.mapper;

import org.springframework.stereotype.Component;
import pl.bussystem.domain.busride.mapper.BusRideMapper;
import pl.bussystem.domain.ticket.model.dto.CreateTicketDTO;
import pl.bussystem.domain.ticket.model.dto.ReadTicketDTO;
import pl.bussystem.domain.ticket.persistence.entity.TicketEntity;
import pl.bussystem.domain.user.mapper.UserMapper;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.function.Function;

@Component
public class TicketMapper {
  public static Function<? super TicketEntity, ? extends ReadTicketDTO> mapToReadTicektDTO =
      entity -> new ReadTicketDTO(
          entity.getId(),
          UserMapper.mapToReadUserDTO.apply(entity.getUserAccount()),
          entity.getName(),
          entity.getSurname(),
          entity.getEmail(),
          entity.getPhone(),
          entity.getDateTime(),
          entity.getPrice(),
          BusRideMapper.mapToReadBusRideDTO.apply(entity.getBusRide()),
          entity.getPaid()
      );

  public TicketEntity mapToTicketEntity(CreateTicketDTO dto) {
    throw new NotImplementedException();
  }
}
