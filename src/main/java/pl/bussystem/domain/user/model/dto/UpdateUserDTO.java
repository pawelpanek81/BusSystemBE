package pl.bussystem.domain.user.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserDTO {
  @Size(min = 1)
  private String username;

  @Size(min = 1)
  private String name;

  @Size(min = 1)
  private String surname;

  @Size(min = 1)
  private String phone;

  @Size(min = 1)
  private String photo;
}
