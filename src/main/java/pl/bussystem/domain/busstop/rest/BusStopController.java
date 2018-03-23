package pl.bussystem.domain.busstop.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pl.bussystem.domain.busstop.model.AddBusStopDTO;
import pl.bussystem.domain.busstop.persistence.entity.BusStopEntity;
import pl.bussystem.domain.busstop.service.BusStopService;

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
  ResponseEntity addNewBusStop(@RequestBody @Valid AddBusStopDTO addBusStopDTO) {
    BusStopEntity busEntity = BusStopEntity.builder()
        .city(addBusStopDTO.getCity())
        .name(addBusStopDTO.getName())
        .build();

    busStopService.addBusStop(busEntity);

    return new ResponseEntity(HttpStatus.CREATED);
  }

  @RequestMapping(path = "/get", method = RequestMethod.GET)
  ResponseEntity getAllBusStop() {
    List<BusStopEntity> busStops = busStopService.findAll();
    return new ResponseEntity<>(busStops, HttpStatus.OK);
  }
}
