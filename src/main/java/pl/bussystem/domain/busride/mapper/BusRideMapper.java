package pl.bussystem.domain.busride.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.bussystem.domain.bus.mapper.BusMapper;
import pl.bussystem.domain.bus.persistence.entity.BusEntity;
import pl.bussystem.domain.bus.persistence.repository.BusRepository;
import pl.bussystem.domain.busride.model.dto.BusTripSearchDTO;
import pl.bussystem.domain.busride.model.dto.CreateBusRideDTO;
import pl.bussystem.domain.busride.model.dto.ReadAssignedRideDTO;
import pl.bussystem.domain.busride.model.dto.ReadBusRideDTO;
import pl.bussystem.domain.busride.persistence.entity.BusRideEntity;
import pl.bussystem.domain.lineinfo.busline.mapper.BusLineMapper;
import pl.bussystem.domain.lineinfo.busline.persistence.entity.BusLineEntity;
import pl.bussystem.domain.lineinfo.busline.persistence.repository.BusLineRepository;
import pl.bussystem.domain.user.mapper.UserMapper;
import pl.bussystem.domain.user.persistence.entity.AccountEntity;
import pl.bussystem.domain.user.persistence.repository.AccountRepository;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Function;

@Component
public class BusRideMapper {
  private BusLineRepository busLineRepository;
  private AccountRepository accountRepository;
  private BusRepository busRepository;

  @Autowired
  public BusRideMapper(BusLineRepository busLineRepository,
                       AccountRepository accountRepository,
                       BusRepository busRepository) {
    this.busLineRepository = busLineRepository;
    this.accountRepository = accountRepository;
    this.busRepository = busRepository;
  }

  public static Function<? super BusRideEntity, ? extends ReadBusRideDTO> mapToReadBusRideDTO =
      entity -> new ReadBusRideDTO(
          entity.getId(),
          entity.getStartDateTime(),
          entity.getEndDateTime(),
          BusLineMapper.mapToReadBusLineDTO.apply(entity.getBusLine()),
          UserMapper.mapToReadUserDTO.apply(entity.getPrimaryDriver()),
          UserMapper.mapToReadUserDTO.apply(entity.getSecondaryDriver()),
          entity.getDriveNettoPrice(),
          entity.getBus() == null ? null : BusMapper.mapToReadBusDTO.apply(entity.getBus()),
          entity.getActive()
      );

  public static Function<? super BusRideEntity, ? extends ReadAssignedRideDTO> mapToReadAssignedRideDTO =
      entity -> new ReadAssignedRideDTO(
          entity.getStartDateTime(),
          entity.getEndDateTime(),
          BusLineMapper.mapToReadBusLineDTO.apply(entity.getBusLine()),
          UserMapper.mapToReadUserDTO.apply(entity.getPrimaryDriver()),
          UserMapper.mapToReadUserDTO.apply(entity.getSecondaryDriver()),
          entity.getBus() == null ? null : BusMapper.mapToReadBusDTO.apply(entity.getBus())
      );

  public static BusTripSearchDTO mapToBusTripSearchDTO(BusRideEntity entity, Double price, Integer seats) {
    return new BusTripSearchDTO(
        entity.getId(),
        entity.getBusLine().getId(),
        entity.getStartDateTime(),
        entity.getEndDateTime(),
        price * seats
    );
  }

  public BusRideEntity mapToBusRideEntity(CreateBusRideDTO dto) {
    Optional<BusLineEntity> optionalOfBusLineEntity = Optional.empty();
    Optional<AccountEntity> optionalOfPrimaryDriver = Optional.empty();
    Optional<AccountEntity> optionalOfSecondaryDriver = Optional.empty();
    Optional<BusEntity> optionalOfBusEntity = Optional.empty();

    if (dto.getBusLine() != null) {
      optionalOfBusLineEntity = busLineRepository.findById(dto.getBusLine());
      if (!optionalOfBusLineEntity.isPresent()) {
        throw new NoSuchElementException();
      }
    }

    if (dto.getPrimaryDriver() != null) {
      optionalOfPrimaryDriver = accountRepository.findById(dto.getPrimaryDriver());
      if (!optionalOfPrimaryDriver.isPresent()) {
        throw new NoSuchElementException();
      }
    }

    if (dto.getSecondaryDriver() != null) {
      optionalOfSecondaryDriver = accountRepository.findById(dto.getSecondaryDriver());
      if (!optionalOfSecondaryDriver.isPresent()) {
        throw new NoSuchElementException();
      }
    }

    if (dto.getBusId() != null) {
      optionalOfBusEntity = busRepository.findById(dto.getBusId());
      if (!optionalOfBusEntity.isPresent()) {
        throw new NoSuchElementException();
      }
    }

    return BusRideEntity.builder()
        .startDateTime(dto.getStartDateTime())
        .endDateTime(dto.getEndDateTime())
        .busLine(optionalOfBusLineEntity.orElse(null))
        .primaryDriver(optionalOfPrimaryDriver.orElse(null))
        .secondaryDriver(optionalOfSecondaryDriver.orElse(null))
        .driveNettoPrice(dto.getDriveNettoPrice())
        .bus(optionalOfBusEntity.orElse(null))
        .build();
  }
}