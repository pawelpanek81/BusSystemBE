package pl.bussystem.domain.bus.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import pl.bussystem.domain.bus.model.dto.BusAddDTO;
import pl.bussystem.domain.bus.persistence.entity.BusEntity;
import pl.bussystem.domain.bus.service.BusService;
import pl.bussystem.rest.exception.RestExceptionCodes;
import pl.bussystem.rest.exception.RestException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/v1.0/bus")
class BusController {

  private BusService busService;

  BusController(BusService busService) {
    this.busService = busService;
  }

  @RequestMapping(path = "/add", method = RequestMethod.POST)
  @Secured(value = {"ROLE_ADMIN"})
  ResponseEntity<RestException> addBus(@RequestBody @Valid BusAddDTO busAddDTO) {
    if (busService.existsByRegistrationNumber(busAddDTO.getRegistrationNumber())) {
      RestException restException = new RestException(RestExceptionCodes.BUS_REGISTRATION_NUMBER_EXISTS,
          "Bus with given registration number already exists");
      return new ResponseEntity<>(restException, HttpStatus.CONFLICT);
    }

    BusEntity busEntity = BusEntity.builder()
        .registrationNumber(busAddDTO.getRegistrationNumber())
        .brand(busAddDTO.getBrand())
        .model(busAddDTO.getModel())
        .seats(busAddDTO.getSeats())
        .build();

    busService.create(busEntity);

    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @RequestMapping(path = "/remove/{id}", method = RequestMethod.DELETE)
  @Secured(value = {"ROLE_ADMIN"})
  ResponseEntity<RestException> removeBus(@PathVariable Integer id) {
    if (!busService.existsById(id)) {
      RestException restException = new RestException(RestExceptionCodes.BUS_WITH_THIS_ID_DOES_NOT_EXISTS,
          "There is no bus with given registration number!");
      return new ResponseEntity<>(restException, HttpStatus.NOT_FOUND);
    }

    busService.removeById(id);

    return new ResponseEntity<>(HttpStatus.OK);
  }

  @RequestMapping(path = "/get-all", method = RequestMethod.GET)
  @Secured(value = {"ROLE_ADMIN"})
  @ResponseBody
  public List<BusEntity> getAllBuses() {
    return busService.findAll();
  }


}
