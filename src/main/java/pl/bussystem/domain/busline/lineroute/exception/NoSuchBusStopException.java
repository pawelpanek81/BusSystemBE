package pl.bussystem.domain.busline.lineroute.exception;

public class NoSuchBusStopException extends RuntimeException {
  public NoSuchBusStopException(String message) {
    super(message);
  }
}
