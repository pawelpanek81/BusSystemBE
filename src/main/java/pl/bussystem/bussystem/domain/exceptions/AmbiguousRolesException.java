package pl.bussystem.bussystem.domain.exceptions;

public class AmbiguousRolesException extends RuntimeException {
  public AmbiguousRolesException(String s) {
    super(s);
  }
}
