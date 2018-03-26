package pl.bussystem.domain.busstop.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import pl.bussystem.domain.busstop.mapper.BusStopMapper;
import pl.bussystem.domain.busstop.model.dto.CreateBusStopDTO;
import pl.bussystem.domain.busstop.model.dto.ReadBusStopDTO;
import pl.bussystem.domain.busstop.persistence.entity.BusStopEntity;
import pl.bussystem.domain.busstop.service.BusStopService;
import pl.bussystem.rest.exception.RestExceptionCodes;
import pl.bussystem.rest.exception.RestException;

import javax.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/v1.0/bus-stops")
class BusStopController {
  private BusStopService busStopService;

  @Autowired
  public BusStopController(BusStopService busStopService) {
    this.busStopService = busStopService;
  }


  @RequestMapping(value = "", method = RequestMethod.POST)
  @Secured(value = {"ROLE_ADMIN"})
  ResponseEntity<RestException> create(@RequestBody @Valid CreateBusStopDTO dto) {
    BusStopEntity busEntity = BusStopMapper.mapToBusStopEntity(dto);

    try {
      busStopService.create(busEntity);
    } catch (IllegalArgumentException e) {
      RestException exceptionDTO = new RestException(RestExceptionCodes.BUS_STOP_WITH_GIVEN_CITY_AND_NAME_EXISTS,
          "Bus stop with this city and name exists in database");
      return new ResponseEntity<>(exceptionDTO, HttpStatus.CONFLICT);
    }

    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @RequestMapping(value = "", method = RequestMethod.GET)
  ResponseEntity <List<ReadBusStopDTO>> readAll() {
    List<BusStopEntity> busStops = busStopService.read();
    List<ReadBusStopDTO> busStopDTOS = busStops.stream()
        .map(BusStopMapper.mapToReadBusStopDTO)
        .collect(Collectors.toList());
    return new ResponseEntity<>(busStopDTOS, HttpStatus.OK);
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
  ResponseEntity<RestException> deleteById(@PathVariable Integer id) {
    try {
      busStopService.deleteById(id);
    } catch (NoSuchElementException e) {
      RestException exceptionDTO = new RestException(RestExceptionCodes.BUS_STOP_WITH_GIVEN_ID_DOES_NOT_EXISTS,
          "Bus stop with this id don't exists in database");
      return new ResponseEntity<>(exceptionDTO, HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
