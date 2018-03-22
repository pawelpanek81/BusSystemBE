package pl.bussystem.domain.user.exception;

public class AmbiguousRolesException extends RuntimeException {
  public AmbiguousRolesException(String s) {
    super(s);
  }
}
