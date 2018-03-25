package pl.bussystem.domain.lineroute.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import pl.bussystem.domain.lineroute.exception.NoSuchBusLineException;
import pl.bussystem.domain.lineroute.exception.NoSuchBusStopException;
import pl.bussystem.domain.lineroute.mapper.LineRouteMapper;
import pl.bussystem.domain.lineroute.model.CreateLineRouteDTO;
import pl.bussystem.domain.lineroute.model.ReadLineRouteDTO;
import pl.bussystem.domain.lineroute.persistence.entity.LineRouteEntity;
import pl.bussystem.domain.lineroute.service.LineRouteService;
import pl.bussystem.rest.exception.RestException;
import pl.bussystem.rest.exception.RestExceptionCodes;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api/v1.0/line-routes")
public class LineRouteController {
  private LineRouteService lineRouteService;
  private LineRouteMapper lineRouteMapper;

  @Autowired
  public LineRouteController(LineRouteService lineRouteService, LineRouteMapper lineRouteMapper) {
    this.lineRouteService = lineRouteService;
    this.lineRouteMapper = lineRouteMapper;
  }

  @RequestMapping(path = "/", method = RequestMethod.POST)
  @Secured(value = {"ROLE_ADMIN"})
  ResponseEntity<RestException> create(@RequestBody CreateLineRouteDTO dto) {
    LineRouteEntity lineRouteEntity = null;
    try {
      lineRouteEntity = lineRouteMapper.mapToLineRouteEntity(dto);
    } catch (NoSuchBusLineException e) {
      RestException restException = new RestException(
        RestExceptionCodes.BUS_LINE_ID_IN_LINE_ROUTE_DOES_NOT_EXISTS,
          "Bus line with id: " + dto.getBusLineId() + " does not exists!"
      );
      return new ResponseEntity<>(restException, HttpStatus.CONFLICT);
    } catch (NoSuchBusStopException e) {
      RestException restException = new RestException(
          RestExceptionCodes.BUS_STOP_ID_IN_LINE_ROUTE_DOES_NOT_EXISTS,
          "Bus stop with id: " + dto.getBusLineId() + " does not exists!"
      );
      return new ResponseEntity<>(restException, HttpStatus.CONFLICT);
    }
    lineRouteService.create(lineRouteEntity);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @RequestMapping(path = "/", method = RequestMethod.GET)
  ResponseEntity<List<ReadLineRouteDTO>> readAll() {
    List<LineRouteEntity> lineRoutes = lineRouteService.read();
    List<ReadLineRouteDTO> lineRouteDTOS = lineRoutes.stream()
        .map(LineRouteMapper.mapToReadLineRouteDTO)
        .collect(Collectors.toList());
    return new ResponseEntity<>(lineRouteDTOS, HttpStatus.OK);
  }

  @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
  @Secured(value = {"ROLE_ADMIN"})
  ResponseEntity<RestException> deleteById(@PathVariable Integer id) {
    try {
      lineRouteService.deleteById(id);
    } catch (NoSuchElementException e) {
      RestException restException = new RestException(
          RestExceptionCodes.LINE_ROUTE_WITH_THAT_ID_DOES_NOT_EXISTS,
          "Line route with id: " + id + " does not exists!");
      return new ResponseEntity<>(restException, HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
