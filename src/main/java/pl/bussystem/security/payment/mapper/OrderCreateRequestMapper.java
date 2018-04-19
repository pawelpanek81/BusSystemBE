package pl.bussystem.security.payment.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.bussystem.domain.ticket.persistence.entity.TicketEntity;
import pl.bussystem.domain.ticket.service.TicketService;
import pl.bussystem.security.payment.model.dto.PaymentDTO;
import pl.bussystem.security.payment.model.dto.TicketDTO;
import pl.bussystem.security.payment.model.payu.common.Buyer;
import pl.bussystem.security.payment.model.payu.common.Product;
import pl.bussystem.security.payment.model.payu.orders.create.request.OrderCreateRequest;
import pl.bussystem.security.payment.model.payu.orders.create.request.Settings;
import pl.bussystem.security.payment.service.PaymentServiceImpl;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static pl.bussystem.security.payment.rest.API.PAYMENTS_NOTIFY_URL;


@Component
public class OrderCreateRequestMapper {
  private PaymentServiceImpl.Credentials credentials;
  private TicketService ticketService;

  @Autowired
  public OrderCreateRequestMapper(PaymentServiceImpl.Credentials credentials,
                                  TicketService ticketService) {
    this.credentials = credentials;
    this.ticketService = ticketService;
  }

  private String getTotalAmount(PaymentDTO dto) {
    Integer price = 0;
    TicketDTO fromTicket = dto.getFromTicket();
    TicketDTO toTicket = dto.getToTicket();

    price = Integer.valueOf(fromTicket.getTicketPrice());

    if (toTicket != null) {
      price += Integer.valueOf(toTicket.getTicketPrice());
    }

    return price.toString();
  }

  public OrderCreateRequest createOrder(PaymentDTO paymentDTO, HttpServletRequest req) {
    Optional<TicketEntity> ticketFrom = ticketService.readById(paymentDTO.getFromTicket().getTicketId());
    Optional<TicketEntity> ticketTo = Optional.empty();
    if (paymentDTO.getToTicket() != null) {
      ticketTo = ticketService.readById(paymentDTO.getToTicket().getTicketId());
    }

    if (!ticketFrom.isPresent()) {
      throw new NoSuchElementException("Ticket with id: " +
          paymentDTO.getFromTicket().getTicketId() + " does not exists");
    }

    List<Product> products = new ArrayList<>();
    products.add(Product.builder()
        .name("Bilet nr: " + ticketFrom.get().getId())
        .unitPrice(paymentDTO.getFromTicket().getTicketPrice())
        .quantity(paymentDTO.getNumberOfPassengers().toString())
        .build());

    ticketTo.ifPresent(ticketEntity -> products.add(
        Product.builder()
            .name("Bilet nr: " + ticketEntity.getId())
            .unitPrice(paymentDTO.getToTicket().getTicketPrice())
            .quantity(paymentDTO.getNumberOfPassengers().toString())
            .build()
    ));

    return OrderCreateRequest.builder()
        .notifyUrl(PAYMENTS_NOTIFY_URL)
        .customerIp(req.getRemoteAddr())
        .merchantPosId(credentials.getPos_id())
        .description(ticketTo.isPresent() ? "Sprzedaż biletów" : "Sprzedaż biletu")
        .currencyCode("PLN")
        .totalAmount(this.getTotalAmount(paymentDTO))
        .buyer(
            Buyer.builder()
                .email(ticketFrom.get().getEmail())
                .phone(ticketFrom.get().getPhone()) // optional
                .firstName(ticketFrom.get().getName()) // optional
                .lastName(ticketFrom.get().getSurname()) // optional
                .language("pl") // optional
                .build())
        .settings(
            Settings.builder()
                .invoiceDisabled(Boolean.TRUE.toString())
                .build()) // optional
        .products(products)
        .build();
  }
}
