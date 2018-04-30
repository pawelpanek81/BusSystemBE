package pl.bussystem.security.payment.rest;

public class API {
  public static final String ORDERS_URL = "https://secure.snd.payu.com/api/v2_1/orders";
  public static final String AUTHORIZATION_URL = "https://secure.snd.payu.com/pl/standard/user/oauth/authorize";
  public static final String PAYMENTS_NOTIFY_URL = "http://januszpol-rest.herokuapp.com/api/v1.0/payments/notify";
  public static final String PAYMENTS_CONTINUE_URL = "http://januszpol-rest.herokuapp.com/api/v1.0/payments/continue";
}
