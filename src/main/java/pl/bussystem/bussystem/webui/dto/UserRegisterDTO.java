package pl.bussystem.bussystem.webui.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserRegisterDTO {
  @NotNull @Size(min = 1)
  private String username;

  @NotNull @Size(min = 1)
  private String name;

  @NotNull @Size(min = 1)
  private String surname;

  @NotNull @Size(min = 1)
  private String password;

  @NotNull @Size(min = 1)
  private String email;

  private String phone;
}
