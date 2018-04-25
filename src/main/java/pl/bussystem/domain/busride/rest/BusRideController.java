package pl.bussystem.domain.busride.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pl.bussystem.domain.busride.mapper.BusRideMapper;
import pl.bussystem.domain.busride.model.dto.CreateBusRideDTO;
import pl.bussystem.domain.busride.model.dto.CreateBusRideFromScheduleAndDatesDTO;
import pl.bussystem.domain.busride.model.dto.ReadBusRideDTO;
import pl.bussystem.domain.busride.persistence.entity.BusRideEntity;
import pl.bussystem.domain.busride.service.BusRideService;
import pl.bussystem.rest.exception.RestException;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/v1.0/bus-rides")
class BusRideController {
  private BusRideService busRideService;
  private BusRideMapper busRideMapper;

  @Autowired
  public BusRideController(BusRideService busRideService,
                           BusRideMapper busRideMapper) {
    this.busRideService = busRideService;
    this.busRideMapper = busRideMapper;
  }

  @RequestMapping(value = "", method = RequestMethod.POST)
  @Secured(value = {"ROLE_ADMIN"})
  ResponseEntity<RestException> create(@RequestBody @Valid CreateBusRideDTO dto) {
    BusRideEntity busRideEntity = busRideMapper.mapToBusRideEntity(dto);
    busRideService.create(busRideEntity);

    return new ResponseEntity<>(HttpStatus.OK);
  }

  @RequestMapping(value = "", method = RequestMethod.GET)
  ResponseEntity<List<ReadBusRideDTO>> readAll() {
    List<BusRideEntity> busRides = busRideService.read();
    List<ReadBusRideDTO> dtos = busRides.stream()
        .map(BusRideMapper.mapToReadBusRideDTO)
        .collect(Collectors.toList());

    return new ResponseEntity<>(dtos, HttpStatus.OK);
  }

  @RequestMapping(value = "/generated", method = RequestMethod.POST)
  ResponseEntity<?> createFromScheduleAndTime(@RequestBody
    @Valid CreateBusRideFromScheduleAndDatesDTO dto) {
      busRideService.autoCreate(dto);

    return new ResponseEntity<>(HttpStatus.OK);
  }
}