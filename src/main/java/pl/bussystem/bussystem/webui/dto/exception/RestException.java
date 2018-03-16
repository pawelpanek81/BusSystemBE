package pl.bussystem.bussystem.webui.dto.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
public class RestException {
  private Integer status;
  private String message;
}
