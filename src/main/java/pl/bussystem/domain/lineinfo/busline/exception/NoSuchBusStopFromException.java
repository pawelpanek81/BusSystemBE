package pl.bussystem.domain.lineinfo.busline.exception;

public class NoSuchBusStopFromException extends RuntimeException {
  public NoSuchBusStopFromException(String message) {
    super(message);
  }
}
