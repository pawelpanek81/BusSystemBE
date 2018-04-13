package pl.bussystem.domain.lineinfo.lineroute.exception;

public class DriveTimeLessThenZeroException extends RuntimeException {
  public DriveTimeLessThenZeroException(String message) {
    super(message);
  }
}
