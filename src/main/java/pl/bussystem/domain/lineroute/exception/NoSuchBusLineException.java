package pl.bussystem.domain.lineroute.exception;

public class NoSuchBusLineException extends RuntimeException {
  public NoSuchBusLineException(String message) {
    super(message);
  }
}
