package pl.bussystem.domain.busline.busline.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import pl.bussystem.domain.busline.busline.exception.NoSuchBusStopFromException;
import pl.bussystem.domain.busline.busline.exception.NoSuchBusStopToException;
import pl.bussystem.domain.busline.busline.mapper.BusLineMapper;
import pl.bussystem.domain.busline.busline.model.dto.CreateBusLineDTO;
import pl.bussystem.domain.busline.busline.model.dto.ReadBusLineDTO;
import pl.bussystem.domain.busline.busline.persistence.entity.BusLineEntity;
import pl.bussystem.domain.busline.busline.service.BusLineService;
import pl.bussystem.domain.busstop.mapper.BusStopMapper;
import pl.bussystem.domain.busstop.model.dto.ReadBusStopDTO;
import pl.bussystem.domain.busstop.persistence.entity.BusStopEntity;
import pl.bussystem.domain.busstop.service.BusStopService;
import pl.bussystem.domain.busline.lineroute.mapper.LineRouteMapper;
import pl.bussystem.domain.busline.lineroute.model.dto.ReadLineRouteDTO;
import pl.bussystem.domain.busline.lineroute.persistence.entity.LineRouteEntity;
import pl.bussystem.domain.busline.lineroute.service.LineRouteService;
import pl.bussystem.domain.busline.schedule.mapper.ScheduleMapper;
import pl.bussystem.domain.busline.schedule.model.dto.ReadScheduleDTO;
import pl.bussystem.domain.busline.schedule.persistence.entity.ScheduleEntity;
import pl.bussystem.domain.busline.schedule.service.ScheduleService;
import pl.bussystem.rest.exception.RestException;
import pl.bussystem.rest.exception.RestExceptionCodes;

import javax.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/v1.0/bus-lines")
class BusLineController {
  private BusLineService busLineService;
  private LineRouteService lineRouteService;
  private ScheduleService scheduleService;
  private BusStopService busStopService;
  private BusLineMapper busLineMapper;

  @Autowired
  public BusLineController(BusLineService busLineService,
                           BusLineMapper busLineMapper,
                           LineRouteService lineRouteService,
                           ScheduleService scheduleService,
                           BusStopService busStopService) {
    this.busLineService = busLineService;
    this.busLineMapper = busLineMapper;
    this.lineRouteService = lineRouteService;
    this.scheduleService = scheduleService;
    this.busStopService = busStopService;
  }

  @RequestMapping(value = "", method = RequestMethod.POST)
  @Secured(value = {"ROLE_ADMIN"})
  ResponseEntity<RestException> create(@RequestBody @Valid CreateBusLineDTO dto) {
    BusLineEntity busLineEntity;
    try {
      busLineEntity = busLineMapper.mapToBusLineEntity(dto);
    } catch (NoSuchBusStopFromException e) {
      RestException restException = new RestException(
          RestExceptionCodes.BUS_STOP_FROM_WITH_GIVEN_ID_DOES_NOT_EXISTS,
          "Bus stop from with id: " + dto.getBusStopFromId() + " or: " + dto.getBusStopToId() + " does not exists!"
      );
      return new ResponseEntity<>(restException, HttpStatus.CONFLICT);
    } catch (NoSuchBusStopToException e) {
      RestException restException = new RestException(
          RestExceptionCodes.BUS_STOP_TO_WITH_GIVEN_ID_DOES_NOT_EXISTS,
          "Bus stop to with id: " + dto.getBusStopFromId() + " or: " + dto.getBusStopToId() + " does not exists!"
      );
      return new ResponseEntity<>(restException, HttpStatus.CONFLICT);
    }
    busLineService.create(busLineEntity);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @RequestMapping(value = "", method = RequestMethod.GET)
  ResponseEntity<List<ReadBusLineDTO>> readAll() {
    List<BusLineEntity> busLines = busLineService.read();
    List<ReadBusLineDTO> busLinesDTOS = busLines.stream()
        .map(BusLineMapper.mapToReadBusLineDTO)
        .collect(Collectors.toList());
    return new ResponseEntity<>(busLinesDTOS, HttpStatus.OK);
  }

  @RequestMapping(value = "{id}", method = RequestMethod.GET)
  ResponseEntity<ReadBusLineDTO> readById(@PathVariable Integer id) {
    BusLineEntity busLine;
    try {
      busLine = busLineService.readById(id);
    } catch (NoSuchElementException e) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    ReadBusLineDTO busLineDTO = BusLineMapper.mapToReadBusLineDTO.apply(busLine);
    return new ResponseEntity<>(busLineDTO, HttpStatus.OK);
  }

  @RequestMapping(value = "{id}/routes", method = RequestMethod.GET)
  ResponseEntity<List<ReadLineRouteDTO>> readRoutes(@PathVariable Integer id) {
    List<LineRouteEntity> routeEntities;
    try {
      routeEntities = lineRouteService.readByBusLineId(id);
    } catch (NoSuchElementException e) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    List<ReadLineRouteDTO> routes = routeEntities.stream()
        .map(LineRouteMapper.mapToReadLineRouteDTO)
        .collect(Collectors.toList());
    return new ResponseEntity<>(routes, HttpStatus.OK);
  }

  @RequestMapping(value = "{id}/schedules", method = RequestMethod.GET)
  ResponseEntity<List<ReadScheduleDTO>> readSchedules(@PathVariable Integer id) {
    List<ScheduleEntity> scheduleEntities;
    try {
      scheduleEntities = scheduleService.readByBusLineId(id);
    } catch (NoSuchElementException e) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    List<ReadScheduleDTO> schedules = scheduleEntities.stream()
        .map(ScheduleMapper.mapToReadScheduleDTO)
        .collect(Collectors.toList());
    return new ResponseEntity<>(schedules, HttpStatus.OK);
  }

  @RequestMapping(value = "{id}/stops", method = RequestMethod.GET)
  ResponseEntity<List<ReadBusStopDTO>> readStops(@PathVariable Integer id) {
    List<BusStopEntity> busStopEntities;
    try {
      busStopEntities = busStopService.readByBusLineId(id);
    } catch (NoSuchElementException e) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    List<ReadBusStopDTO> busStops = busStopEntities.stream()
        .map(BusStopMapper.mapToReadBusStopDTO)
        .collect(Collectors.toList());
    return new ResponseEntity<>(busStops, HttpStatus.OK);
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
  @Secured(value = {"ROLE_ADMIN"})
  ResponseEntity<RestException> deleteById(@PathVariable Integer id) {
    try {
      busLineService.deleteById(id);
    } catch (NoSuchElementException e) {
      RestException restException = new RestException(
          RestExceptionCodes.BUS_LINE_WITH_GIVEN_ID_DOES_NOT_EXISTS,
          "Bus line with id: " + id + " does not exists!"
      );
      return new ResponseEntity<>(restException, HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(HttpStatus.OK);
  }
}