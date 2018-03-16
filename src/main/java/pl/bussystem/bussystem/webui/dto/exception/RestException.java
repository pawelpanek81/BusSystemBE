package pl.bussystem.bussystem.webui.dto.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RestException {
  private Integer status;
  private String message;
}
