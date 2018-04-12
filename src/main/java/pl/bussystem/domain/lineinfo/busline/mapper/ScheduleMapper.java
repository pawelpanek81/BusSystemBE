package pl.bussystem.domain.lineinfo.busline.mapper;

import pl.bussystem.domain.lineinfo.busline.model.dto.ReadScheduleDTO;
import pl.bussystem.domain.lineinfo.schedule.persistence.entity.ScheduleEntity;

import java.util.function.Function;

public class ScheduleMapper {

  public static Function<? super ScheduleEntity, ? extends ReadScheduleDTO> mapToReadScheduleDTO =
      entity -> new ReadScheduleDTO(
          entity.getId(),
          entity.getCode(),
          entity.getStartHour(),
          entity.getEnabled()
      );
}
