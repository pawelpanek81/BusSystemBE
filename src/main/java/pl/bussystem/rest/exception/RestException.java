package pl.bussystem.rest.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RestException {
  private Integer status;
  private String message;
}