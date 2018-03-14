package pl.bussystem.bussystem.webui.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserRegisterDTO {
  private String username;
  private String name;
  private String surname;
  private String password;
  private String email;
  private String phone;
}
