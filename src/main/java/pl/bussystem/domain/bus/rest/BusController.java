package pl.bussystem.domain.bus.rest;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import pl.bussystem.domain.bus.mapper.BusMapper;
import pl.bussystem.domain.bus.model.dto.CreateBusDTO;
import pl.bussystem.domain.bus.model.dto.ReadBusDTO;
import pl.bussystem.domain.bus.persistence.entity.BusEntity;
import pl.bussystem.domain.bus.service.BusService;
import pl.bussystem.rest.exception.RestExceptionCodes;
import pl.bussystem.rest.exception.RestException;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1.0/buses")
class BusController {

  private BusService busService;

  BusController(BusService busService) {
    this.busService = busService;
  }

  @RequestMapping(value = "", method = RequestMethod.POST)
  @Secured(value = {"ROLE_ADMIN"})
  ResponseEntity<RestException> create(@RequestBody @Valid CreateBusDTO dto) {
    if (busService.existsByRegistrationNumber(dto.getRegistrationNumber())) {
      RestException restException = new RestException(RestExceptionCodes.BUS_WITH_GIVEN_REGISTRATION_NUMBER_EXISTS,
          "Bus with registration number: " + dto.getRegistrationNumber() + " already exists");
      return new ResponseEntity<>(restException, HttpStatus.CONFLICT);
    }
    BusEntity busEntity = BusMapper.mapToBusEntity(dto);
    busService.create(busEntity);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
  @Secured(value = {"ROLE_ADMIN"})
  ResponseEntity<RestException> deleteById(@PathVariable Integer id) {
    try {
      busService.removeById(id);
    } catch (EmptyResultDataAccessException e) {
      RestException restException = new RestException(RestExceptionCodes.BUS_WITH_GIVEN_ID_DOES_NOT_EXISTS,
          "There is no bus with id: " + id);
      return new ResponseEntity<>(restException, HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @RequestMapping(value = "", method = RequestMethod.GET)
  @Secured(value = {"ROLE_ADMIN"})
  ResponseEntity<List<ReadBusDTO>> readAll() {
    List<BusEntity> buses = busService.read();
    List<ReadBusDTO> busDTOS = buses.stream()
        .map(BusMapper.mapToReadBusDTO)
        .collect(Collectors.toList());

    return new ResponseEntity<>(busDTOS, HttpStatus.OK);
  }


}
