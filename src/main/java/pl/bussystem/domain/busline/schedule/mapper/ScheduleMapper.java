package pl.bussystem.domain.busline.schedule.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.bussystem.domain.busline.busline.mapper.BusLineMapper;
import pl.bussystem.domain.busline.busline.persistence.entity.BusLineEntity;
import pl.bussystem.domain.busline.busline.persistence.repository.BusLineRepository;
import pl.bussystem.domain.busline.lineroute.exception.NoSuchBusLineException;
import pl.bussystem.domain.busline.schedule.model.dto.CreateScheduleDTO;
import pl.bussystem.domain.busline.schedule.model.dto.ReadScheduleDTO;
import pl.bussystem.domain.busline.schedule.persistence.entity.ScheduleEntity;

import java.util.Optional;
import java.util.function.Function;

@Component
public class ScheduleMapper {
  private BusLineRepository busLineRepository;

  @Autowired
  public ScheduleMapper(BusLineRepository busLineRepository) {
    this.busLineRepository = busLineRepository;
  }

  public static Function<? super ScheduleEntity, ? extends ReadScheduleDTO> mapToReadScheduleDTO =
      entity -> new ReadScheduleDTO(
          entity.getId(),
          BusLineMapper.mapToReadBusLineDTO.apply(entity.getBusLine()),
          entity.getCode(),
          entity.getStartHour(),
          entity.getEnabled()
      );

  public ScheduleEntity mapToScheduleEntity(CreateScheduleDTO dto) {
    Optional<BusLineEntity> busLine = busLineRepository.findById(dto.getBusLineId());

    if (!busLine.isPresent()) {
      throw new NoSuchBusLineException("Bus line with id: " + dto.getBusLineId() + "doesn't exists!");
    }

    return ScheduleEntity.builder()
        .busLine(busLine.get())
        .code(dto.getCode())
        .startHour(dto.getStartHour())
        .enabled(dto.getEnabled())
        .build();
  }
}
