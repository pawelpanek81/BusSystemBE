package pl.bussystem.domain.lineinfo.busline.exception;

public class NoSuchBusStopToException extends RuntimeException {
  public NoSuchBusStopToException(String message) {
    super(message);
  }
}
