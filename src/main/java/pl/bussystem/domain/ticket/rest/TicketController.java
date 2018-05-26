package pl.bussystem.domain.ticket.rest;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.bussystem.domain.busride.persistence.entity.BusRideEntity;
import pl.bussystem.domain.busride.service.BusRideService;
import pl.bussystem.domain.ticket.mapper.TicketMapper;
import pl.bussystem.domain.ticket.model.dto.CreateTicketsOrderDTO;
import pl.bussystem.domain.ticket.model.dto.CreatedTicketIdsDTO;
import pl.bussystem.domain.ticket.model.dto.ReadAvailableTicketsDTO;
import pl.bussystem.domain.ticket.persistence.entity.TicketEntity;
import pl.bussystem.domain.ticket.service.TicketService;
import pl.bussystem.domain.user.persistence.entity.AccountEntity;
import pl.bussystem.domain.user.service.AccountService;
import pl.bussystem.rest.exception.RestException;
import pl.bussystem.rest.exception.RestExceptionCodes;

import javax.servlet.ServletContext;
import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1.0/tickets")
class TicketController {
  private TicketService ticketService;
  private TicketMapper ticketMapper;
  private BusRideService busRideService;
  private AccountService accountService;

  @Autowired
  TicketController(TicketService ticketService, TicketMapper ticketMapper, BusRideService busRideService, AccountService accountService) {
    this.ticketService = ticketService;
    this.ticketMapper = ticketMapper;
    this.busRideService = busRideService;
    this.accountService = accountService;
  }

  @RequestMapping(value = "", method = RequestMethod.GET)
  ResponseEntity<List<ReadAvailableTicketsDTO>> read() {
    List<TicketEntity> read = ticketService.read();
    List<ReadAvailableTicketsDTO> dtos = read.stream()
        .map(TicketMapper.mapToReadTicketDTO)
        .collect(Collectors.toList());

    return new ResponseEntity<>(dtos, HttpStatus.OK);
  }

  @RequestMapping(value = "", method = RequestMethod.POST)
  ResponseEntity<?> buyTicket(@RequestBody @Valid CreateTicketsOrderDTO dto,
                              Principal principal) {
    CreatedTicketIdsDTO returnedDTO = new CreatedTicketIdsDTO();
    AccountEntity accountEntity = null;
    try {
      accountEntity = accountService.findAccountByPrincipal(principal);
    } catch (NullPointerException ignored) {
    }
    BusRideEntity rideTo;
    try {
      rideTo = busRideService.readById(dto.getRideToId());
    } catch (NoSuchElementException exc) {
      RestException restException = new RestException(RestExceptionCodes.NO_SUCH_RIDE_TO,
          "There is no rideTo with given ID");
      return new ResponseEntity<>(restException, HttpStatus.NOT_FOUND);
    }
    if (rideTo.getStartDateTime().isBefore(LocalDateTime.now())) {
      RestException restException = new RestException(RestExceptionCodes.BUS_HAS_ALREADY_LEFT,
          "Bus departure time is in the past");
      return new ResponseEntity<>(restException, HttpStatus.BAD_REQUEST);
    }
    TicketEntity ticketTo = ticketMapper.mapToTicketEntity(dto, accountEntity, rideTo);
    if (busRideService.getFreeSeats(rideTo) < ticketTo.getSeats()) {
      RestException restException = new RestException(RestExceptionCodes.NOT_ENOUGH_SEATS_IN_RIDE_TO,
          "There is no enough seats in rideTo");
      return new ResponseEntity<>(restException, HttpStatus.NOT_FOUND);
    }

    if (dto.getRideBackId() != null) {
      BusRideEntity rideBack;
      try {
        rideBack = busRideService.readById(dto.getRideBackId());
      } catch (NoSuchElementException exc) {
        RestException restException = new RestException(RestExceptionCodes.NO_SUCH_RIDE_BACK,
            "There is no rideBack with given ID");
        return new ResponseEntity<>(restException, HttpStatus.NOT_FOUND);
      }
      if (!rideBack.getStartDateTime().isAfter(rideTo.getStartDateTime())) {
        RestException restException = new RestException(RestExceptionCodes.RIDE_BACK_IS_EARLIER,
            "Ride back is earlier");
        return new ResponseEntity<>(restException, HttpStatus.BAD_REQUEST);
      }

      TicketEntity ticketBack = ticketMapper.mapToTicketEntity(dto, accountEntity, rideTo);
      if (busRideService.getFreeSeats(rideBack) < ticketBack.getSeats()) {
        RestException restException = new RestException(RestExceptionCodes.NOT_ENOUGH_SEATS_IN_RIDE_BACK,
            "There is no enough seats in rideBack");
        return new ResponseEntity<>(restException, HttpStatus.NOT_FOUND);
      }

      TicketEntity backTicketEntity = ticketService.create(ticketBack);
      returnedDTO.setBackTicket(backTicketEntity.getId());
    }
    TicketEntity toTicketEntity = ticketService.create(ticketTo);
    returnedDTO.setToTicket(toTicketEntity.getId());

    return new ResponseEntity<>(returnedDTO, HttpStatus.CREATED);
  }

  @RequestMapping(value = "by-user", method = RequestMethod.GET)
  ResponseEntity<List<ReadAvailableTicketsDTO>> readByPrincipal(Principal principal) {
    if (principal == null) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    List<ReadAvailableTicketsDTO> dtos = ticketService.readUserTickets(principal).stream()
        .map(TicketMapper.mapToReadTicketDTO)
        .collect(Collectors.toList());
    return new ResponseEntity<>(dtos, HttpStatus.OK);
  }

  @RequestMapping(value = "{id}", method = RequestMethod.GET, produces = MediaType.IMAGE_PNG_VALUE)
  ResponseEntity<byte[]> getQRCode(@PathVariable Integer id) throws Exception {
    String path = ticketService.generateQRCode(id);
    FileSystemResource file = new FileSystemResource(path);
    byte[] content = new byte[(int)file.contentLength()];
    IOUtils.read(file.getInputStream(), content);

    return ResponseEntity.ok()
        .contentType(MediaType.IMAGE_PNG)
        .contentLength(file.contentLength())
        .body(content);
  }
}
