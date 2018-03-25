package pl.bussystem.domain.user.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReadUserDTO {
  private Integer id;
  private String username;
  private String name;
  private String surname;
  private String email;
  private String phone;
  private String photo;
}