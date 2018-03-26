package pl.bussystem.domain.schedule.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.bussystem.domain.schedule.service.ScheduleService;

@RestController
@RequestMapping(path = "/api/v1.0/schedules")
public class ScheduleController {
  private ScheduleService scheduleService;

  @Autowired
  public ScheduleController(ScheduleService scheduleService) {
    this.scheduleService = scheduleService;
  }


}
