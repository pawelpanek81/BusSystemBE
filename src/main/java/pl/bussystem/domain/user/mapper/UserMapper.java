package pl.bussystem.domain.user.mapper;

import pl.bussystem.domain.user.model.dto.CreateUserDTO;
import pl.bussystem.domain.user.model.dto.ReadUserDTO;
import pl.bussystem.domain.user.model.dto.UpdateUserDTO;
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

  public static AccountEntity mapToAccountEntity(AccountEntity entity, UpdateUserDTO dto) {
    String newUsername =  dto.getUsername()  == null ? entity.getUsername() : dto.getUsername();
    String newName =      dto.getName()      == null ? entity.getName()     : dto.getName();
    String newSurName =   dto.getSurname()   == null ? entity.getSurname()  : dto.getSurname();
    String newPhone =     dto.getPhone()     == null ? entity.getPhone()    : dto.getPhone();
    String newPhoto =     dto.getPhoto()     == null ? entity.getPhoto()    : dto.getPhoto();

    return AccountEntity.builder()
        .id(entity.getId())
        .username(newUsername)
        .name(newName)
        .surname(newSurName)
        .password(entity.getPassword())
        .email(entity.getEmail())
        .phone(newPhone)
        .active(entity.getActive())
        .photo(newPhoto)
        .build();
  }
}
