package pl.bussystem.security.payment.service;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.MessageDigestPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import pl.bussystem.security.payment.model.payu.common.Product;
import pl.bussystem.security.payment.model.payu.oauth.authorization.AuthenticationResponse;
import pl.bussystem.security.payment.model.payu.orders.create.request.OrderCreateRequest;
import pl.bussystem.security.payment.model.payu.orders.create.response.OrderCreateResponse;
import pl.bussystem.security.payment.model.payu.orders.notification.Notification;
import pl.bussystem.security.payment.rest.API;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {
  private Credentials credentials;
  private AuthenticationResponse authResponse;
  private LocalDateTime lastSuccessfullyAuthDateTime;

  @Getter
  @Setter
  @Component
  public static class Credentials {
    private final String grant_type;
    private final String pos_id;
    private final String client_id;
    private final String second_key;
    private final String client_secret;

    public Credentials(@Value("${payu.grant_type}") String grant_type,
                       @Value("${payu.pos_id}") String pos_id,
                       @Value("${payu.client_id}") String client_id,
                       @Value("${payu.second_key}") String second_key,
                       @Value("${payu.client_secret}") String client_secret) {
      this.grant_type = grant_type;
      this.pos_id = pos_id;
      this.client_id = client_id;
      this.second_key = second_key;
      this.client_secret = client_secret;
    }


    MultiValueMap<String, String> getAuthenticationAsMap() {
      MultiValueMap<String, String> data = new LinkedMultiValueMap<>();
      data.add("grant_type", grant_type);
      data.add("client_id", client_id);
      data.add("client_secret", client_secret);
      return data;
    }
  }

  @Autowired
  public PaymentServiceImpl(Credentials credentials) {
    this.credentials = credentials;
    try {
      lastSuccessfullyAuthDateTime = LocalDateTime.now();
      this.authResponse = this.authenticate();
    } catch (HttpClientErrorException e) {
      System.out.println(e.getStatusCode());
      System.out.println(e.getResponseBodyAsString());
      this.lastSuccessfullyAuthDateTime = null;
    }
  }

  @Override
  public AuthenticationResponse authenticate() {
    RestTemplate restTemplate = new RestTemplate();
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

    MultiValueMap<String, String> requestBody = credentials.getAuthenticationAsMap();

    HttpEntity<?> request = new HttpEntity<>(requestBody, headers);

    ResponseEntity<AuthenticationResponse> response = restTemplate
        .postForEntity(API.AUTHORIZATION_URL, request, AuthenticationResponse.class);

    return response.getBody();
  }

  @Override
  public void consumeNotification(Notification notification, HttpServletRequest request) {
    // TODO SIGNATURE CHECKING
    String stringToHash = "test";

    PasswordEncoder encoder = new MessageDigestPasswordEncoder("MD5");
    Method digest = ReflectionUtils.findMethod(MessageDigestPasswordEncoder.class, "digest", String.class, CharSequence.class);
    ReflectionUtils.makeAccessible(digest);
    String encoded = null;
    try {
      encoded = (String) digest.invoke(encoder, "", stringToHash);
    } catch (IllegalAccessException | InvocationTargetException e) {
      e.printStackTrace();
    }

    Integer i = 5;

    List<Product> products = notification.getOrder().getProducts();
    products.forEach(System.out::println);
  }

  @Override // TODO ASPECT
  public Boolean isAuthenticationTokenExpired() {
    return LocalDateTime.now().isAfter(lastSuccessfullyAuthDateTime.plusSeconds(authResponse.getExpires_in()));
  }

  @Override
  public Boolean isAuthenticatedSuccessfully() {
    return lastSuccessfullyAuthDateTime != null;
  }

  @Override
  public void renewAuthentication() {
    try {
      lastSuccessfullyAuthDateTime = LocalDateTime.now();
      this.authResponse = this.authenticate();
    } catch (HttpClientErrorException e) {
      System.out.println(e.getStatusCode());
      System.out.println(e.getResponseBodyAsString());
      this.lastSuccessfullyAuthDateTime = null;
      throw new RuntimeException("Authentication error");
    }
  }

  @Override
  public ResponseEntity<OrderCreateResponse> payForATicket(OrderCreateRequest orderCreateRequest) {
    if (!this.isAuthenticatedSuccessfully() || this.isAuthenticationTokenExpired()) { // TODO ASPECT
      this.renewAuthentication();
    }

    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-Type", "application/json");
    String headerAuthorizationValue = authResponse.getToken_type() + " " + authResponse.getAccess_token();
    headers.add("Authorization", headerAuthorizationValue);

    RestTemplate restTemplate = new RestTemplate();
    HttpEntity<?> request = new HttpEntity<>(orderCreateRequest, headers);

    return restTemplate.postForEntity(API.ORDERS_URL, request, OrderCreateResponse.class);
  }
}