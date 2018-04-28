package pl.bussystem.domain.busride.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;
import pl.bussystem.domain.busride.mapper.BusRideMapper;
import pl.bussystem.domain.busride.model.dto.BusJourneySearchDTO;
import pl.bussystem.domain.busride.model.dto.CreateBusRideDTO;
import pl.bussystem.domain.busride.model.dto.CreateBusRideFromScheduleAndDatesDTO;
import pl.bussystem.domain.busride.model.dto.ReadBusRideDTO;
import pl.bussystem.domain.busride.persistence.entity.BusRideEntity;
import pl.bussystem.domain.busride.service.BusRideService;
import pl.bussystem.domain.busstop.persistence.entity.BusStopEntity;
import pl.bussystem.domain.busstop.service.BusStopService;
import pl.bussystem.domain.user.persistence.repository.AccountRepository;
import pl.bussystem.rest.exception.RestException;

import javax.validation.Valid;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/v1.0/bus-rides")
class BusRideController {
  private BusRideService busRideService;
  private BusRideMapper busRideMapper;
  private AccountRepository accountRepository;
  private BusStopService busStopService;

  @Autowired
  public BusRideController(BusRideService busRideService,
                           BusRideMapper busRideMapper,
                           AccountRepository accountRepository,
                           BusStopService busStopService) {
    this.busRideService = busRideService;
    this.busRideMapper = busRideMapper;
    this.accountRepository = accountRepository;
    this.busStopService = busStopService;
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

  @RequestMapping(value = "/{id}", method = RequestMethod.PATCH)
  ResponseEntity<?> updateDrivers(@PathVariable Integer id, @RequestBody Map<String, Object> fields) {
    BusRideEntity busRideEntity = busRideService.readById(id);

    fields.forEach((k, v) -> {
      if (k.equals("primaryDriver") || k.equals("secondaryDriver")) {
        v = accountRepository.findById((Integer) v).orElse(null);
        Field field = ReflectionUtils.findField(BusRideEntity.class, k);
        field.setAccessible(true);
        ReflectionUtils.setField(field, busRideEntity, v);
        field.setAccessible(false);
      }
    });
    busRideService.update(busRideEntity);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @RequestMapping(value = "/search", method = RequestMethod.GET)
  ResponseEntity<BusJourneySearchDTO> searchForRides(@RequestParam("from") int from,
                                                     @RequestParam("to") int to,
                                                     @RequestParam("departureDate") LocalDate departureDate,
                                                     @RequestParam("returnDate") LocalDate returnDate,
                                                     @RequestParam("seats") Integer seats) {
    BusStopEntity stopFrom = busStopService.readById(from);
    BusStopEntity stopTo = busStopService.readById(to);
    List<ReadBusRideDTO> departureRides = busRideService.read().stream()
        .filter(ride -> ride.getStartDateTime().toLocalDate().equals(departureDate))
        .filter(ride -> busRideService.containConnection(ride, stopFrom, stopTo))
        .filter(ride -> busRideService.getFreeSeats(ride) >= seats)
        .map(BusRideMapper.mapToReadBusRideDTO)
        .collect(Collectors.toList());

    List<ReadBusRideDTO> returnRides = busRideService.read().stream()
        .filter(ride -> ride.getStartDateTime().toLocalDate().equals(returnDate))
        .filter(ride -> busRideService.containConnection(ride, stopTo, stopFrom))
        .filter(ride -> busRideService.getFreeSeats(ride) >= seats)
        .map(BusRideMapper.mapToReadBusRideDTO)
        .collect(Collectors.toList());

    return new ResponseEntity<>(new BusJourneySearchDTO(departureRides, returnRides), HttpStatus.OK);
  }

}