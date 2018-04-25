package pl.bussystem.security.payment.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import pl.bussystem.security.payment.model.dto.PaymentDTO;
import pl.bussystem.security.payment.model.payu.oauth.authorization.AuthenticationResponse;
import pl.bussystem.security.payment.model.payu.orders.create.request.OrderCreateRequest;
import pl.bussystem.security.payment.model.payu.orders.create.response.OrderCreateResponse;
import pl.bussystem.security.payment.model.payu.orders.notification.Notification;

public interface PaymentService {

  AuthenticationResponse authenticate();

  ResponseEntity<OrderCreateResponse> payForATicket(OrderCreateRequest req);

  void consumeNotification(Notification notification, HttpEntity<String> request);

  Boolean isAuthenticationTokenExpired();

  Boolean isAuthenticatedSuccessfully();

  void renewAuthentication();

  Boolean checkFrontendSignature(PaymentDTO dto);
}
