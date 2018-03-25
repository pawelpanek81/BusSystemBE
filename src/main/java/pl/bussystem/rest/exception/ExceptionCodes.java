package pl.bussystem.rest.exception;

public class ExceptionCodes {
  public static final int USERNAME_TAKEN = 1;
  public static final int EMAIL_ALREADY_USED = 2;
  public static final int USERNAME_TAKEN_OR_EMAIL_ALREADY_USED = 3;
  public static final int BUS_REGISTRATION_ALREADY_EXISTS = 4;
  public static final int BUS_STOP_WITH_CITY_AND_NAME_EXISTS = 5;
  public static final int BUS_WITH_ID_DOESNT_EXISTS = 6;
}
