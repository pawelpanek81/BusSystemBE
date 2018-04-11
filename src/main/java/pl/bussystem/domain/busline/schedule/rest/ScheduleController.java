package pl.bussystem.domain.busline.schedule.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import pl.bussystem.domain.busline.lineroute.exception.NoSuchBusLineException;
import pl.bussystem.domain.busline.schedule.mapper.ScheduleMapper;
import pl.bussystem.domain.busline.schedule.model.dto.CreateScheduleDTO;
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
@RequestMapping(value = "/api/v1.0/schedules")
class ScheduleController {
  private ScheduleService scheduleService;
  private ScheduleMapper scheduleMapper;

  @Autowired
  public ScheduleController(ScheduleService scheduleService, ScheduleMapper scheduleMapper) {
    this.scheduleService = scheduleService;
    this.scheduleMapper = scheduleMapper;
  }

  @RequestMapping(path = "", method = RequestMethod.POST)
  @Secured(value = {"ROLE_ADMIN"})
  ResponseEntity<RestException> create(@Valid @RequestBody CreateScheduleDTO dto) {
    ScheduleEntity scheduleEntity;
    try {
      scheduleEntity = scheduleMapper.mapToScheduleEntity(dto);

    } catch (NoSuchBusLineException e) {
      RestException restException = new RestException(
          RestExceptionCodes.BUS_LINE_WITH_GIVEN_ID_DOES_NOT_EXISTS,
          "Bus line with id: " + dto.getBusLineId() + " does not exists!"
      );
      return new ResponseEntity<>(restException, HttpStatus.NOT_FOUND);
    }

    scheduleService.create(scheduleEntity);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @RequestMapping(path = "", method = RequestMethod.GET)
  ResponseEntity<List<ReadScheduleDTO>> readAll() {
    List<ScheduleEntity> scheduleEntities = scheduleService.read();
    List<ReadScheduleDTO> dtos = scheduleEntities.stream()
        .map(ScheduleMapper.mapToReadScheduleDTO)
        .collect(Collectors.toList());
    return new ResponseEntity<>(dtos, HttpStatus.OK);
  }

  @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
  ResponseEntity<RestException> deleteById(@PathVariable Integer id) {
    try {
      scheduleService.deleteById(id);
    } catch (NoSuchElementException e) {
      RestException exception = new RestException(
          RestExceptionCodes.SCHEDULE_WITH_GIVEN_ID_DOES_NOT_EXISTS,
          "Schedule with id: " + id + " does not exists!");
      return new ResponseEntity<>(exception, HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
