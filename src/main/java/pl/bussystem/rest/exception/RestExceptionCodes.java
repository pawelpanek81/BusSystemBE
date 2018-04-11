package pl.bussystem.rest.exception;

public class RestExceptionCodes {
  /* ACCOUNTS - LOGIN */
  public static final int USERNAME_IS_TAKEN = 1;

  /* ACCOUNTS - REGISTRATION */
  public static final int EMAIL_IS_ALREADY_USED = 2;
  public static final int USERNAME_IS_TAKEN_OR_EMAIL_IS_ALREADY_USED = 3;

  /* BUS STOPS */
  public static final int BUS_STOP_WITH_GIVEN_CITY_AND_NAME_EXISTS = 5;
  public static final int BUS_STOP_WITH_GIVEN_ID_DOES_NOT_EXISTS = 7;

  /* BUS LINES */
  public static final int BUS_LINE_WITH_GIVEN_ID_DOES_NOT_EXISTS = 8;
  public static final int BUS_STOP_FROM_WITH_GIVEN_ID_DOES_NOT_EXISTS = 12;
  public static final int BUS_STOP_TO_WITH_GIVEN_ID_DOES_NOT_EXISTS = 13;

  /* SCHEDULE */
  public static final int SCHEDULE_WITH_GIVEN_ID_DOES_NOT_EXISTS = 14;



  /* LINE ROUTES */
  public static final int LINE_ROUTE_WITH_GIVEN_ID_DOES_NOT_EXISTS = 9;
  public static final int BUS_STOP_ID_IN_LINE_ROUTE_DOES_NOT_EXISTS = 10;
  public static final int BUS_LINE_ID_IN_LINE_ROUTE_DOES_NOT_EXISTS = 11;

  /* BUSES */
  public static final int BUS_WITH_GIVEN_REGISTRATION_NUMBER_EXISTS = 4;
  public static final int BUS_WITH_GIVEN_ID_DOES_NOT_EXISTS = 6;

  /* BUS RIDES */

  /* TICKETS */


  /* BUS RIDES */


  /* NEWS */
  public static final int NEWS_WITH_GIVEN_ID_DOES_NOT_EXISTS = 7;


}