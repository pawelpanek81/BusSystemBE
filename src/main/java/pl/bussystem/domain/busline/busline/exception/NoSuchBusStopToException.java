package pl.bussystem.domain.busline.busline.exception;

public class NoSuchBusStopToException extends RuntimeException {
  public NoSuchBusStopToException(String message) {
    super(message);
  }
}
