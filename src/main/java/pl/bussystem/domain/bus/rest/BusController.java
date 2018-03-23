package pl.bussystem.domain.bus.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.bussystem.domain.bus.model.dto.BusAddDTO;
import pl.bussystem.domain.bus.model.dto.RemoveBusDTO;
import pl.bussystem.domain.bus.persistence.entity.BusEntity;
import pl.bussystem.domain.bus.service.BusService;
import pl.bussystem.rest.exception.ExceptionCodes;
import pl.bussystem.rest.exception.RestException;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1.0/bus")
class BusController {

  private BusService busService;

  BusController(BusService busService) {
    this.busService = busService;
  }

  @PostMapping("/add")
  @Secured(value = {"ROLE_ADMIN"})
  ResponseEntity<RestException> addBus(@RequestBody @Valid BusAddDTO busAddDTO) {
    if (busService.existsByRegistrationNumber(busAddDTO.getRegistrationNumber())) {
      RestException restException = new RestException(ExceptionCodes.BUS_REGISTRATION_ALREADY_EXISTS,
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

  @PostMapping("/remove")
  @Secured(value = {"ROLE_ADMIN"})
  ResponseEntity<RestException> removeBus(@RequestBody @Valid RemoveBusDTO removeBusDTO){
    if (!busService.existsByRegistrationNumber(removeBusDTO.getRegistrationNumber())){
      RestException restException = new RestException(ExceptionCodes.BUS_DOES_NOT_EXISTS,
          "There is no bus with given registration number!");
      return new ResponseEntity<>(restException, HttpStatus.NOT_FOUND);
    }

    busService.removeByRegistrationNumber(removeBusDTO.getRegistrationNumber());

    return new ResponseEntity<>(HttpStatus.OK);
  }


}
