package pl.bussystem.domain.busline.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import pl.bussystem.domain.busline.exception.NoSuchBusStopFromException;
import pl.bussystem.domain.busline.exception.NoSuchBusStopToException;
import pl.bussystem.domain.busline.mapper.BusLineMapper;
import pl.bussystem.domain.busline.model.CreateBusLineDTO;
import pl.bussystem.domain.busline.model.ReadBusLineDTO;
import pl.bussystem.domain.busline.persistence.entity.BusLineEntity;
import pl.bussystem.domain.busline.service.BusLineService;
import pl.bussystem.domain.lineroute.exception.NoSuchBusStopException;
import pl.bussystem.rest.exception.RestException;
import pl.bussystem.rest.exception.RestExceptionCodes;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api/v1.0/busline")
class BusLineController {
  private BusLineService busLineService;
  private BusLineMapper busLineMapper;

  @Autowired
  public BusLineController(BusLineService busLineService, BusLineMapper busLineMapper) {
    this.busLineService = busLineService;
    this.busLineMapper = busLineMapper;
  }

  @RequestMapping(path = "/create", method = RequestMethod.POST)
  @Secured(value = {"ROLE_ADMIN"})
  ResponseEntity<RestException> create(@RequestBody CreateBusLineDTO dto) {
    BusLineEntity busLineEntity = null;
    try {
      busLineEntity = busLineMapper.mapToBusLineEntity(dto);
    } catch (NoSuchBusStopFromException e) {
      RestException restException = new RestException(
          RestExceptionCodes.BUS_STOP_FROM_WITH_THAT_ID_DOES_NOT_EXISTS,
          "Bus stop from with id: " + dto.getBusStopFromId() + " or: " + dto.getBusStopToId() + " does not exists!"
      );
      return new ResponseEntity<>(restException, HttpStatus.CONFLICT);
    } catch (NoSuchBusStopToException e) {
      RestException restException = new RestException(
          RestExceptionCodes.BUS_STOP_TO_WITH_THAT_ID_DOES_NOT_EXISTS,
          "Bus stop to with id: " + dto.getBusStopFromId() + " or: " + dto.getBusStopToId() + " does not exists!"
      );
      return new ResponseEntity<>(restException, HttpStatus.CONFLICT);
    }
    busLineService.create(busLineEntity);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @RequestMapping(path = "/read", method = RequestMethod.GET)
  ResponseEntity<List<ReadBusLineDTO>> readAll() {
    List<BusLineEntity> busLines = busLineService.read();
    List<ReadBusLineDTO> busLinesDTOS = busLines.stream()
        .map(BusLineMapper.mapToReadBusLineDTO)
        .collect(Collectors.toList());
    return new ResponseEntity<>(busLinesDTOS, HttpStatus.OK);
  }

  @RequestMapping(path = "/delete/{id}", method = RequestMethod.DELETE)
  @Secured(value = {"ROLE_ADMIN"})
  ResponseEntity<RestException> deleteById(@PathVariable Integer id) {
    try {
      busLineService.deleteById(id);
    } catch (NoSuchElementException e) {
      RestException restException = new RestException(
          RestExceptionCodes.BUS_LINE_WITH_THAT_ID_DOES_NOT_EXISTS,
          "Bus line with id: " + id + " does not exists!"
      );
      return new ResponseEntity<>(restException, HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(HttpStatus.OK);
  }
}