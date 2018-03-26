package pl.bussystem.domain.lineroute.exception;

public class NoSuchBusStopException extends RuntimeException {
  public NoSuchBusStopException(String message) {
    super(message);
  }
}
