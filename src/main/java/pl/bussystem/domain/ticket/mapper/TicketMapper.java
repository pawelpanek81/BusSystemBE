package pl.bussystem.domain.ticket.mapper;

import org.springframework.stereotype.Component;
import pl.bussystem.domain.busride.mapper.BusRideMapper;
import pl.bussystem.domain.busride.persistence.entity.BusRideEntity;
import pl.bussystem.domain.ticket.model.dto.CreateTicketDTO;
import pl.bussystem.domain.ticket.model.dto.ReadTicketDTO;
import pl.bussystem.domain.ticket.persistence.entity.TicketEntity;
import pl.bussystem.domain.user.mapper.UserMapper;
import pl.bussystem.domain.user.persistence.entity.AccountEntity;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Component
public class TicketMapper {
  public static Function<? super TicketEntity, ? extends ReadTicketDTO> mapToReadTicketDTO =
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

  public TicketEntity mapToTicketEntity(CreateTicketDTO dto, AccountEntity account, BusRideEntity ride) {
    LocalDateTime localDateTime = LocalDateTime.now();
    return TicketEntity.builder()
        .userAccount(account)
        .name(dto.getName())
        .surname(dto.getSurname())
        .email(dto.getEmail())
        .phone(dto.getPhone())
        .dateTime(localDateTime)
        .price(ride.getDriveNettoPrice() * dto.getSeats())
        .seats(dto.getSeats())
        .busRide(ride)
        .paid(Boolean.FALSE)
        .build();

  }
}
