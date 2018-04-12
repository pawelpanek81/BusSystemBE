package pl.bussystem.domain.lineinfo.lineroute.exception;

public class NoSuchBusLineException extends RuntimeException {
  public NoSuchBusLineException(String message) {
    super(message);
  }
}
