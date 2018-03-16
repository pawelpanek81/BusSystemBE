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
public class CheckUsernameFreeDTO {
  @NotNull
  @Size(min = 1)
  String username;
}
