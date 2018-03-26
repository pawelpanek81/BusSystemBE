package pl.bussystem.domain.bus.exception;

public class NoSuchBusException extends RuntimeException {
  public NoSuchBusException(String message) {
    super(message);
  }
}
