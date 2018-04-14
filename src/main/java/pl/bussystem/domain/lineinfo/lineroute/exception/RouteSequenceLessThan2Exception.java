package pl.bussystem.domain.lineinfo.lineroute.exception;

public class RouteSequenceLessThan2Exception extends RuntimeException {
  public RouteSequenceLessThan2Exception(String message) {
    super(message);
  }
}
