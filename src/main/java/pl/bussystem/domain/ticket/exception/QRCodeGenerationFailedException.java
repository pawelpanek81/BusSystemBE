package pl.bussystem.domain.ticket.exception;

public class QRCodeGenerationFailedException extends Exception {
  public QRCodeGenerationFailedException(String s) {
    super(s);
  }
}
