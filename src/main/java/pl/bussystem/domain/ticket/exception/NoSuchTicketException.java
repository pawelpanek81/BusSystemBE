package pl.bussystem.domain.ticket.exception;

public class NoSuchTicketException extends RuntimeException {
  public NoSuchTicketException(String s) {
    super(s);
  }
}
