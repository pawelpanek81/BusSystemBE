package pl.bussystem.domain.user.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserRegisterDTO {
  @NotBlank
  private String username;

  @NotBlank
  private String name;

  @NotBlank
  private String surname;

  @NotBlank
  private String password;

  @NotBlank
  @Email
  private String email;

  private String phone;
}
