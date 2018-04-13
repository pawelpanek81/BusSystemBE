package pl.bussystem.domain.lineinfo.lineroute.exception;

public class RouteSequenceGreaterThanLastPlusOneException extends RuntimeException {
  public RouteSequenceGreaterThanLastPlusOneException(String message) {
    super(message);
  }
}
