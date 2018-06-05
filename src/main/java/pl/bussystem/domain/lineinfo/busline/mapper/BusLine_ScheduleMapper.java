package pl.bussystem.domain.lineinfo.busline.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.bussystem.domain.lineinfo.busline.model.dto.CreateScheduleDTO;
import pl.bussystem.domain.lineinfo.busline.model.dto.ReadScheduleDTO;
import pl.bussystem.domain.lineinfo.busline.persistence.entity.BusLineEntity;
import pl.bussystem.domain.lineinfo.busline.persistence.repository.BusLineRepository;
import pl.bussystem.domain.lineinfo.lineroute.exception.NoSuchBusLineException;
import pl.bussystem.domain.lineinfo.schedule.persistence.entity.ScheduleEntity;

import java.util.Optional;
import java.util.function.Function;

@Component
public class BusLine_ScheduleMapper {
  private BusLineRepository busLineRepository;

  @Autowired
  public BusLine_ScheduleMapper(BusLineRepository busLineRepository) {
    this.busLineRepository = busLineRepository;
  }

  public static Function<? super ScheduleEntity, ? extends ReadScheduleDTO> mapToReadScheduleDTO =
      entity -> new ReadScheduleDTO(
          entity.getId(),
          entity.getCode(),
          entity.getStartHour(),
          entity.getDriveNettoPrice(),
          entity.getEnabled()
      );

  public ScheduleEntity mapToScheduleEntity(CreateScheduleDTO dto, Integer busLineId) {
    Optional<BusLineEntity> busLine = busLineRepository.findById(busLineId);

    if (!busLine.isPresent()) {
      throw new NoSuchBusLineException("Bus line with id: " + busLineId + "doesn't exists!");
    }

    return ScheduleEntity.builder()
        .busLine(busLine.get())
        .code(dto.getCode())
        .startHour(dto.getStartHour())
        .enabled(true)
        .driveNettoPrice(dto.getDriveNettoPrice())
        .build();
  }
}
