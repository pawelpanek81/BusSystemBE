package pl.bussystem.domain.busline.exception;

public class NoSuchBusStopFromException extends RuntimeException {
  public NoSuchBusStopFromException(String message) {
    super(message);
  }
}
