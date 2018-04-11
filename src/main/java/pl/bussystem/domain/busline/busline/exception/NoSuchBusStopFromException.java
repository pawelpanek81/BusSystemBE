package pl.bussystem.domain.busline.busline.exception;

public class NoSuchBusStopFromException extends RuntimeException {
  public NoSuchBusStopFromException(String message) {
    super(message);
  }
}
