package pl.bussystem.domain.user.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountUpdateDTO {
  private String username;
  private String name;
  private String surname;
  private String phone;
  private String photo;
  private String password;
}