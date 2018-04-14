package pl.bussystem.domain.lineinfo.lineroute.exception;

public class BusLineContainsBusStopException extends RuntimeException {
  public BusLineContainsBusStopException(String message) {
    super(message);
  }
}
