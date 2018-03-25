package pl.bussystem.domain.user.mapper;

import pl.bussystem.domain.user.model.dto.CreateUserDTO;
import pl.bussystem.domain.user.model.dto.ReadUserDTO;
import pl.bussystem.domain.user.persistence.entity.AccountEntity;

import java.util.function.Function;

public class UserMapper {

  public static Function<? super AccountEntity, ? extends ReadUserDTO> mapToReadUserDTO =
      entity -> new ReadUserDTO(
          entity.getId(),
          entity.getUsername(),
          entity.getName(),
          entity.getSurname(),
          entity.getEmail(),
          entity.getPhone(),
          entity.getPhoto());
}
