package pl.bussystem.rest.exception;

public class RestExceptionCodes {
  public static final int USERNAME_IS_TAKEN = 1;
  public static final int EMAIL_IS_ALREADY_USED = 2;
  public static final int USERNAME_IS_TAKEN_OR_EMAIL_IS_ALREADY_USED = 3;

  public static final int BUS_STOP_WITH_THAT_CITY_AND_NAME_EXISTS = 5;
  public static final int BUS_STOP_WITH_THAT_ID_DOES_NOT_EXISTS = 7;

  public static final int BUS_LINE_WITH_THAT_ID_DOES_NOT_EXISTS = 8;

  public static final int BUS_REGISTRATION_NUMBER_EXISTS = 4;
  public static final int BUS_WITH_THIS_ID_DOES_NOT_EXISTS = 6;

}