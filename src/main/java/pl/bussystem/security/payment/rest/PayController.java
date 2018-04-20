package pl.bussystem.security.payment.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.bussystem.security.payment.mapper.OrderCreateRequestMapper;
import pl.bussystem.security.payment.model.dto.PaymentDTO;
import pl.bussystem.security.payment.model.dto.TicketDTO;
import pl.bussystem.security.payment.model.payu.common.Buyer;
import pl.bussystem.security.payment.model.payu.common.Product;
import pl.bussystem.security.payment.model.payu.orders.create.request.OrderCreateRequest;
import pl.bussystem.security.payment.model.payu.orders.create.request.Settings;
import pl.bussystem.security.payment.model.payu.orders.notification.Notification;
import pl.bussystem.security.payment.service.PaymentService;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@RestController
@RequestMapping(value = "api/v1.0/payments")
public class PayController {
  private PaymentService paymentService;
  private OrderCreateRequestMapper orderCreateRequest;

  @Autowired
  public PayController(PaymentService paymentService,
                       OrderCreateRequestMapper orderCreateRequest) {
    this.paymentService = paymentService;
    this.orderCreateRequest = orderCreateRequest;
  }

  @RequestMapping(value = "", method = RequestMethod.GET)
  public ResponseEntity<?> pay(HttpServletRequest request) { //@RequestBody PaymentDTO dto,
    PaymentDTO dto = new PaymentDTO(
        new TicketDTO(1, "1000"),
        null,
        1,
        "asd"
    );

    OrderCreateRequest order = orderCreateRequest.createOrder(dto, request);
    return paymentService.payForATicket(order);
  }

  @RequestMapping(value = "/notify", method = RequestMethod.POST)
  public ResponseEntity<?> authorize(@RequestBody Notification notification, HttpServletRequest request) {
    paymentService.consumeNotification(notification, request);
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
