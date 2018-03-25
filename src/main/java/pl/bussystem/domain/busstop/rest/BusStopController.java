package pl.bussystem.domain.busstop.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import pl.bussystem.domain.busstop.model.BusStopDTO;
import pl.bussystem.domain.busstop.persistence.entity.BusStopEntity;
import pl.bussystem.domain.busstop.service.BusStopService;
import pl.bussystem.rest.exception.RestExceptionCodes;
import pl.bussystem.rest.exception.RestException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/api/v1.0/busstop")
class BusStopController {
  private BusStopService busStopService;

  @Autowired
  public BusStopController(BusStopService busStopService) {
    this.busStopService = busStopService;
  }


  @RequestMapping(path = "/add", method = RequestMethod.POST)
  @Secured(value = {"ROLE_ADMIN"})
  ResponseEntity addNewBusStop(@RequestBody @Valid BusStopDTO busStopDTO) {
    BusStopEntity busEntity = new BusStopEntity(busStopDTO);

    try {
      busStopService.addBusStop(busEntity);
    } catch (IllegalArgumentException e) {
      RestException exceptionDTO = new RestException(RestExceptionCodes.BUS_STOP_WITH_THAT_CITY_AND_NAME_EXISTS,
          "Bus stop with this city and name exists in database");
      return new ResponseEntity<>(exceptionDTO, HttpStatus.CONFLICT);
    }

    return new ResponseEntity(HttpStatus.CREATED);
  }

  @RequestMapping(path = "/get", method = RequestMethod.GET)
  ResponseEntity getAllBusStop() {
    List<BusStopEntity> busStops = busStopService.findAll();
    return new ResponseEntity<>(busStops, HttpStatus.OK);
  }

  @RequestMapping(path = "/del/{id}", method = RequestMethod.DELETE)
  ResponseEntity removeBusStop(@PathVariable Integer id) {
    if (busStopService.removeBusStop(id)) {
      return new ResponseEntity(HttpStatus.OK);
    } else {
      RestException exceptionDTO = new RestException(RestExceptionCodes.BUS_WITH_THIS_ID_DOES_NOT_EXISTS,
          "Bus with this id dont exists in database");
      return new ResponseEntity<>(exceptionDTO, HttpStatus.NOT_FOUND);
    }
  }
}
