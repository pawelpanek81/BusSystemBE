package pl.bussystem.domain.ticket.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pl.bussystem.domain.ticket.mapper.TicketMapper;
import pl.bussystem.domain.ticket.model.dto.CreateTicketDTO;
import pl.bussystem.domain.ticket.model.dto.ReadTicketDTO;
import pl.bussystem.domain.ticket.persistence.entity.TicketEntity;
import pl.bussystem.domain.ticket.service.TicketService;
import pl.bussystem.rest.exception.RestException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1.0/tickets")
class TicketController {
  private TicketService ticketService;
  private TicketMapper ticketMapper;

  @Autowired
  TicketController(TicketService ticketService, TicketMapper ticketMapper) {
    this.ticketService = ticketService;
    this.ticketMapper = ticketMapper;
  }

  @RequestMapping(value = "", method = RequestMethod.GET)
  ResponseEntity<List<ReadTicketDTO>> read() {
    List<TicketEntity> read = ticketService.read();
    List<ReadTicketDTO> dtos = read.stream()
        .map(TicketMapper.mapToReadTicektDTO)
        .collect(Collectors.toList());

    return new ResponseEntity<>(dtos, HttpStatus.OK);
  }

  @RequestMapping(value = "", method = RequestMethod.POST)
  ResponseEntity<RestException> buyTicket(@RequestBody CreateTicketDTO dto) {
    TicketEntity ticketEntity = ticketMapper.mapToTicketEntity(dto);
    return null;
  }
}
