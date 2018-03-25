package pl.bussystem.domain.lineroute.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import pl.bussystem.domain.lineroute.model.CreateLineRouteDTO;
import pl.bussystem.domain.lineroute.model.ReadLineRouteDTO;
import pl.bussystem.domain.lineroute.service.LineRouteService;
import pl.bussystem.rest.exception.RestException;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1.0/lineroute")
public class LineRouteController {
  private LineRouteService lineRouteService;

  @Autowired
  public LineRouteController(LineRouteService lineRouteService) {
    this.lineRouteService = lineRouteService;
  }

  @RequestMapping(path = "/create", method = RequestMethod.POST)
  @Secured(value = {"ROLE_ADMIN"})
  ResponseEntity<RestException> create(@RequestBody CreateLineRouteDTO dto) {
    return null;
  }

  @RequestMapping(path = "/read", method = RequestMethod.GET)
  ResponseEntity<List<ReadLineRouteDTO>> readAll() {
    return null;
  }

  @RequestMapping(path = "/delete/{id}", method = RequestMethod.DELETE)
  @Secured(value = {"ROLE_ADMIN"})
  ResponseEntity<RestException> deleteById(@PathVariable Integer id) {
    return null;
  }
}
