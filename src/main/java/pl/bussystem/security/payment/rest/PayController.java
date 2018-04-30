package pl.bussystem.security.payment.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import pl.bussystem.security.payment.mapper.OrderCreateRequestMapper;
import pl.bussystem.security.payment.model.dto.PaymentDTO;
import pl.bussystem.security.payment.model.payu.orders.create.request.OrderCreateRequest;
import pl.bussystem.security.payment.model.payu.orders.notification.Notification;
import pl.bussystem.security.payment.service.PaymentService;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "api/v1.0/payments")
public class PayController {
  private PaymentService paymentService;
  private OrderCreateRequestMapper orderCreateRequest;
  private ObjectMapper objectMapper = new ObjectMapper();
  private static final Logger logger = LoggerFactory.getLogger(PayController.class);

  @Autowired
  public PayController(PaymentService paymentService,
                       OrderCreateRequestMapper orderCreateRequest) {
    this.paymentService = paymentService;
    this.orderCreateRequest = orderCreateRequest;
  }

  @RequestMapping(value = "", method = RequestMethod.POST)
  @ResponseBody
  public ResponseEntity<?> pay(@RequestBody PaymentDTO dto, HttpServletRequest request) {
    OrderCreateRequest order;
    if (!paymentService.isFrontendSignatureValid(dto)) {
      logger.error("Frontend signature fail");
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    try {
      order = orderCreateRequest.createOrder(dto, request);
    } catch (RuntimeException e) {
      logger.error("Create order fail");
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    return paymentService.payForATicket(order);
  }

  @RequestMapping(value = "/notify", method = RequestMethod.POST)
  public ModelAndView notify(HttpEntity<String> request) {
    ModelAndView mav = new ModelAndView("paymentStatus");
    try {
      Notification notification = objectMapper.readValue(request.getBody(), Notification.class);
      paymentService.consumeNotification(notification, request);
    } catch (Exception e) {
      e.printStackTrace();
      mav.addObject("status", "Płatność zakończona niepowodzeniem, skontaktuj się z administratorem");
      return mav;
    }
    mav.addObject("status", "Płatność zakończona sukcesem, możesz zamknąć kartę");
    return mav;
  }
}
