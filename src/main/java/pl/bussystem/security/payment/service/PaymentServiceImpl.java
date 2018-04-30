package pl.bussystem.security.payment.service;

import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import pl.bussystem.domain.ticket.service.TicketService;
import pl.bussystem.security.payment.model.dto.PaymentDTO;
import pl.bussystem.security.payment.model.payu.oauth.authorization.AuthenticationResponse;
import pl.bussystem.security.payment.model.payu.orders.create.request.OrderCreateRequest;
import pl.bussystem.security.payment.model.payu.orders.create.response.OrderCreateResponse;
import pl.bussystem.security.payment.model.payu.orders.notification.Notification;
import pl.bussystem.security.payment.rest.API;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class PaymentServiceImpl implements PaymentService {
  private Credentials credentials;
  private AuthenticationResponse authResponse;
  private LocalDateTime lastSuccessfullyAuthDateTime;
  private TicketService ticketService;
  private static final Logger logger = LoggerFactory.getLogger(PaymentServiceImpl.class);

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
  public PaymentServiceImpl(Credentials credentials, TicketService ticketService) {
    this.credentials = credentials;
    try {
      lastSuccessfullyAuthDateTime = LocalDateTime.now();
      this.authResponse = this.authenticate();
    } catch (HttpClientErrorException e) {
      logger.error("PayU Authentication error");
      System.out.println(e.getStatusCode());
      System.out.println(e.getResponseBodyAsString());
      this.lastSuccessfullyAuthDateTime = null;
    }
    this.ticketService = ticketService;
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
  public void consumeNotification(Notification notification, HttpEntity<String> request) {
    String openPayuSignatureHeader = request.getHeaders().get("openpayu-signature").get(0);
    Map<String, String> openPayuSignature = new LinkedHashMap<>();
    for (String keyValue : openPayuSignatureHeader.split(";")) {
      String[] pairs = keyValue.split("=", 2);
      openPayuSignature.put(pairs[0], pairs.length == 1 ? "" : pairs[1]);
    }

    if (!openPayuSignature.get("algorithm").equals("MD5")) {
      logger.error("PayU used other than MD5 algorithm in signature");
      throw new RuntimeException("Not implemented");
    }

    if (!isSignatureValid(request, openPayuSignature)) {
      logger.error("Signature not valid");
      throw new RuntimeException("expectedSignature different than actualSignature");
    }

    if (notification.getOrder().getStatus().equals("COMPLETED")) {
      logger.info("Got COMPLETED notification, trying to make ticket paid");
      String extOrderId = notification.getOrder().getExtOrderId();

      String[] ticketIds = extOrderId.split(",");
      Integer ticketDestinationId = Integer.valueOf(ticketIds[0]);
      Integer ticketReturnId = ticketIds.length > 1 ? Integer.valueOf(ticketIds[1]) : null;

      logger.info("Setting paid flag on ticketDestination");
      ticketService.makeTicketPaid(ticketDestinationId);
      if (ticketReturnId != null) {
        logger.info("Setting paid flag on ticketReturn");
        ticketService.makeTicketPaid(ticketReturnId);
      }
      logger.info("Setting paid flag done.");
    }
  }

  public Boolean isSignatureValid(HttpEntity<String> request, Map<String, String> openPayuSignature) {
    String expectedSignature = openPayuSignature.get("signature");
    String actualSignature = md5(request.getBody() + credentials.getSecond_key());
    return expectedSignature.equals(actualSignature);
  }

  @SuppressWarnings("deprecation")
  private String md5(String stringToHash) {
    PasswordEncoder encoder = new org.springframework.security.crypto.password.MessageDigestPasswordEncoder("MD5");
    Method digest = ReflectionUtils.findMethod(org.springframework.security.crypto.password.MessageDigestPasswordEncoder.class, "digest", String.class, CharSequence.class);
    ReflectionUtils.makeAccessible(digest);
    String encoded = null;
    try {
      encoded = (String) digest.invoke(encoder, "", stringToHash);
    } catch (IllegalAccessException | InvocationTargetException e) {
      e.printStackTrace();
    }
    return encoded;
  }

  @Override
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
  public Boolean isFrontendSignatureValid(PaymentDTO dto) {
    return true;
  }

  @Override
  public ResponseEntity<OrderCreateResponse> payForATicket(OrderCreateRequest orderCreateRequest) {
    if (!this.isAuthenticatedSuccessfully() || this.isAuthenticationTokenExpired()) {
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