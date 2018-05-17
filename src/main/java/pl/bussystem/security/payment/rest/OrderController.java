package pl.bussystem.security.payment.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pl.bussystem.domain.user.service.AccountService;
import pl.bussystem.security.payment.mapper.OrderMapper;
import pl.bussystem.security.payment.model.dto.ReadOrderDTO;
import pl.bussystem.security.payment.service.OrderService;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1.0/tickets")
public class OrderController {
  private OrderService orderService;
  private AccountService accountService;

  @Autowired
  public OrderController(OrderService orderService, AccountService accountService) {
    this.orderService = orderService;
    this.accountService = accountService;
  }

  @RequestMapping(value = "", method = RequestMethod.GET)
  ResponseEntity<List<ReadOrderDTO>> readUserOrders(Principal principal) {
    if (principal == null) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    List<ReadOrderDTO> dtos = orderService
        .readByAccountEntity(accountService.findAccountByPrincipal(principal))
        .stream()
        .map(OrderMapper::mapToReadOrderDTO)
        .collect(Collectors.toList());

    return new ResponseEntity<>(dtos, HttpStatus.OK);
  }
}
