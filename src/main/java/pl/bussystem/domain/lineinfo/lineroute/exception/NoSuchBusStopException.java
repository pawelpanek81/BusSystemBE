package pl.bussystem.domain.lineinfo.lineroute.exception;

public class NoSuchBusStopException extends RuntimeException {
  public NoSuchBusStopException(String message) {
    super(message);
  }
}
