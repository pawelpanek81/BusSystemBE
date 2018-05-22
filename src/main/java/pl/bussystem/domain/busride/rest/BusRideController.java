package pl.bussystem.domain.busride.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import pl.bussystem.domain.busride.mapper.BusRideMapper;
import pl.bussystem.domain.busride.model.dto.*;
import pl.bussystem.domain.busride.persistence.entity.BusRideEntity;
import pl.bussystem.domain.busride.service.BusRideService;
import pl.bussystem.domain.busstop.mapper.BusStopMapper;
import pl.bussystem.domain.busstop.model.dto.ReadBusStopDTO;
import pl.bussystem.domain.busstop.persistence.entity.BusStopEntity;
import pl.bussystem.domain.busstop.service.BusStopService;
import pl.bussystem.domain.user.persistence.entity.AccountEntity;
import pl.bussystem.domain.user.service.AccountService;
import pl.bussystem.rest.exception.RestException;
import pl.bussystem.rest.exception.RestExceptionCodes;

import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/v1.0/bus-rides")
class BusRideController {
  private BusRideService busRideService;
  private BusRideMapper busRideMapper;
  private AccountService accountService;
  private BusStopService busStopService;
  private static final Logger logger = LoggerFactory.getLogger(BusRideController.class);

  @Autowired
  public BusRideController(BusRideService busRideService,
                           BusRideMapper busRideMapper,
                           AccountService accountService,
                           BusStopService busStopService) {
    this.busRideService = busRideService;
    this.busRideMapper = busRideMapper;
    this.accountService = accountService;
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
  ResponseEntity<Page<ReadBusRideDTO>> readCustom(@RequestParam(value = "type", required = false) String type,
                                                  @RequestParam(value = "period", required = false) String period,
                                                  @RequestParam(value = "lineId", required = false) Integer lineId,
                                                  Pageable page) {

    Page<BusRideEntity> busRides = busRideService.readCustom(page, type, period, lineId);
    Page<ReadBusRideDTO> dtos = busRides.map(BusRideMapper.mapToReadBusRideDTO);
    return new ResponseEntity<>(dtos, HttpStatus.OK);
  }

  @RequestMapping(value = "/generated", method = RequestMethod.POST)
  ResponseEntity<?> createFromScheduleAndTime(@RequestBody
                                              @Valid CreateBusRideFromScheduleAndDatesDTO dto) {
    logger.info("/generated StartDateTime: " + dto.getStartDateTime().toString());
    logger.info("/generated EndDateTime: " + dto.getEndDateTime().toString());
    busRideService.autoCreate(dto);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.PATCH)
  ResponseEntity<?> updateDrivers(@PathVariable Integer id, @RequestBody Map<String, Object> fields) {
    try {
      busRideService.patch(id, fields);
    } catch (NoSuchElementException e) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @RequestMapping(value = "/search", method = RequestMethod.GET)
  ResponseEntity<?> searchForRides(@RequestParam("from") int from,
                                   @RequestParam("to") int to,
                                   @RequestParam("departureDate")
                                   @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate departureDate,
                                   @RequestParam(name = "returnDate", required = false)
                                   @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate returnDate,
                                   @RequestParam("seats") Integer seats) {

    BusStopEntity stopFrom = busStopService.readById(from);
    BusStopEntity stopTo = busStopService.readById(to);
    if (stopFrom == null) {
      RestException restException = new RestException(RestExceptionCodes.BUS_STOP_WITH_GIVEN_ID_DOES_NOT_EXISTS,
          "There is no stop with ID " + from);
      return new ResponseEntity<>(restException, HttpStatus.NOT_FOUND);
    }
    if (stopTo == null) {
      RestException restException = new RestException(RestExceptionCodes.BUS_STOP_WITH_GIVEN_ID_DOES_NOT_EXISTS,
          "There is no stop with ID " + to);
      return new ResponseEntity<>(restException, HttpStatus.NOT_FOUND);
    }
    if (seats == null || seats < 1) {
      RestException restException = new RestException(RestExceptionCodes.INVALID_NUMBER_OF_SEATS,
          "You must specify number of seats and it have to be grater than 0");
      return new ResponseEntity<>(restException, HttpStatus.NOT_FOUND);
    }

    ReadBusStopDTO stopFromDTO = BusStopMapper.mapToReadBusStopDTO.apply(stopFrom);
    ReadBusStopDTO stopToDTO = BusStopMapper.mapToReadBusStopDTO.apply(stopTo);

    List<BusTripSearchDTO> departureRides = busRideService
        .readActiveRidesFromToWhereEnoughtSeats(stopFrom, stopTo, departureDate,
            seats, LocalDateTime.now())
        .stream()
        .map(entity -> BusRideMapper.mapToBusTripSearchDTO(entity, busRideService.calculateTicketPrice(entity, stopFrom, stopTo), seats))
        .collect(Collectors.toList());

    List<BusTripSearchDTO> returnRides = new ArrayList<>();
    if (returnDate != null && !departureRides.isEmpty()) {
      returnRides = busRideService
          .readActiveRidesFromToWhereEnoughtSeats(stopTo, stopFrom, returnDate,
              seats, departureRides.get(0).getEndDateTime())
          .stream()
          .map(entity -> BusRideMapper.mapToBusTripSearchDTO(entity, busRideService.calculateTicketPrice(entity, stopTo, stopFrom), seats))
          .collect(Collectors.toList());
    }
    return new ResponseEntity<>(new BusJourneySearchDTO(departureRides, returnRides, stopFromDTO, stopToDTO), HttpStatus.OK);
  }

  @RequestMapping(value = "/byDriver", method = RequestMethod.GET)
  ResponseEntity<List<ReadAssignedRideDTO>> readRidesAssignedToLoggedDriver(Principal principal) {
    if (principal == null) {
      return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
    String userType = accountService.getUserType(principal.getName());
    AccountEntity account = accountService.getUserByUsername(principal.getName());
    if (userType.equals("DRIVER")) {
      List<BusRideEntity> entities = busRideService.readFuture();
      List<ReadAssignedRideDTO> result = entities.stream()
          .filter(br -> br.getPrimaryDriver() != null && br.getPrimaryDriver().equals(account) ||
              br.getSecondaryDriver() != null && br.getSecondaryDriver().equals(account))
          .map(BusRideMapper.mapToReadAssignedRideDTO)
          .collect(Collectors.toList());

      return new ResponseEntity<>(result, HttpStatus.OK);
    } else return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
  }
}