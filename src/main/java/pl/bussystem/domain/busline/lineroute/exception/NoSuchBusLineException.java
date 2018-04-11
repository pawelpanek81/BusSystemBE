package pl.bussystem.domain.busline.lineroute.exception;

public class NoSuchBusLineException extends RuntimeException {
  public NoSuchBusLineException(String message) {
    super(message);
  }
}
