package pl.bussystem.security.payment.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pl.bussystem.security.payment.model.dto.PaymentDTO;
import pl.bussystem.security.payment.model.payu.common.Buyer;
import pl.bussystem.security.payment.model.payu.common.Product;
import pl.bussystem.security.payment.model.payu.orders.create.request.OrderCreateRequest;
import pl.bussystem.security.payment.model.payu.orders.create.request.Settings;
import pl.bussystem.security.payment.model.payu.orders.notification.Notification;
import pl.bussystem.security.payment.service.PaymentService;

import java.util.Arrays;

@RestController
@RequestMapping(value = "api/v1.0/payments")
public class PayController {
  private PaymentService paymentService;

  @Autowired
  public PayController(PaymentService paymentService) {
    this.paymentService = paymentService;
  }

  @RequestMapping(value = "", method = RequestMethod.GET)
  public ResponseEntity<?> pay(PaymentDTO dto) {
    OrderCreateRequest orderCreateRequest = OrderCreateRequest.builder()
        .notifyUrl("https://your.eshop.com/notify")
        .customerIp("127.0.0.1")
        .merchantPosId("300746")
        .description("RTV market")
        .currencyCode("PLN")
        .totalAmount("21000")
        .buyer(
            Buyer.builder()
                .email("john.doe@example.com")
                .phone("654111654")
                .firstName("John")
                .lastName("Doe")
                .language("pl")
                .build())
        .settings(
            Settings.builder()
                .invoiceDisabled(Boolean.TRUE.toString())
                .build())
        .products(
            Arrays.asList(
                Product.builder()
                    .name("Wireless Mouse for Laptop")
                    .unitPrice("15000")
                    .quantity("1")
                    .build(),
                Product.builder()
                    .name("HDMI cable")
                    .unitPrice("6000")
                    .quantity("1")
                    .build()))
        .build();

    return paymentService.payForATicket(orderCreateRequest);
  }

  @RequestMapping(value = "/notify", method = RequestMethod.POST)
  public ResponseEntity<?> authorize(Notification notification) {
    paymentService.consumeNotification(notification);
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
