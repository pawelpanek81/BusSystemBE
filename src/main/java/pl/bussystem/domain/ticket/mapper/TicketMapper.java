package pl.bussystem.domain.ticket.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.bussystem.domain.busride.mapper.BusRideMapper;
import pl.bussystem.domain.busride.persistence.entity.BusRideEntity;
import pl.bussystem.domain.busride.service.BusRideService;
import pl.bussystem.domain.busstop.persistence.entity.BusStopEntity;
import pl.bussystem.domain.busstop.service.BusStopService;
import pl.bussystem.domain.ticket.model.dto.CreateTicketsOrderDTO;
import pl.bussystem.domain.ticket.model.dto.ReadAvailableTicketsDTO;
import pl.bussystem.domain.ticket.persistence.entity.TicketEntity;
import pl.bussystem.domain.user.mapper.UserMapper;
import pl.bussystem.domain.user.persistence.entity.AccountEntity;

import java.time.LocalDateTime;
import java.util.function.Function;

@Component
public class TicketMapper {
  private BusRideService busRideService;
  private BusStopService busStopService;

  @Autowired
  public TicketMapper(BusRideService busRideService, BusStopService busStopService) {
    this.busRideService = busRideService;
    this.busStopService = busStopService;
  }

  public static Function<? super TicketEntity, ? extends ReadAvailableTicketsDTO> mapToReadTicketDTO =
      entity -> new ReadAvailableTicketsDTO(
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

  public TicketEntity mapToTicketEntity(CreateTicketsOrderDTO dto,
                                        AccountEntity account,
                                        BusRideEntity ride) {
    LocalDateTime localDateTime = LocalDateTime.now();
    BusStopEntity from = busStopService.readById(dto.getFromBusStopId());
    BusStopEntity destination = busStopService.readById(dto.getDestinationBusStopId());
    Double singlePrice = busRideService.calculateTicketPrice(ride, from, destination);
    return TicketEntity.builder()
        .userAccount(account)
        .name(dto.getName())
        .surname(dto.getSurname())
        .email(dto.getEmail())
        .phone(dto.getPhone())
        .dateTime(localDateTime)
        .price(singlePrice * dto.getSeats())
        .seats(dto.getSeats())
        .busRide(ride)
        .fromBusStop(from)
        .destBusStop(destination)
        .paid(Boolean.FALSE)
        .build();

  }
}
