package pl.bussystem.security.payment.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import static pl.bussystem.security.payment.rest.API.PAYMENTS_CONTINUE_URL;
import static pl.bussystem.security.payment.rest.API.PAYMENTS_NOTIFY_URL;


@Component
public class OrderCreateRequestMapper {
  private PaymentServiceImpl.Credentials credentials;
  private TicketService ticketService;
  private static final Logger logger = LoggerFactory.getLogger(OrderCreateRequestMapper.class);

  @Autowired
  public OrderCreateRequestMapper(PaymentServiceImpl.Credentials credentials,
                                  TicketService ticketService) {
    this.credentials = credentials;
    this.ticketService = ticketService;
  }

  private String getTotalAmount(PaymentDTO dto) {
    Double price = 0.0;
    TicketDTO fromTicket = dto.getDepartureTicket();
    TicketDTO toTicket = dto.getReturnTicket();

    price = Double.valueOf(fromTicket.getTicketPrice());

    if (toTicket != null) {
      price += Double.valueOf(toTicket.getTicketPrice());
    }
    return convertPriceToSmallestUnit(price);
  }

  private String convertPriceToSmallestUnit(Double dPrice) {
    dPrice = dPrice * 100;
    Integer returnPrice = dPrice.intValue();
    return String.valueOf(returnPrice);
  }

  private String convertPriceToSmallestUnit(String price) {
    Double dPrice = Double.valueOf(price);
    dPrice = dPrice * 100;
    Integer returnPrice = dPrice.intValue();
    return String.valueOf(returnPrice);
  }

  public OrderCreateRequest createOrder(PaymentDTO paymentDTO, HttpServletRequest req) {
    Optional<TicketEntity> ticketFrom = ticketService.readById(paymentDTO.getDepartureTicket().getTicketId());
    Optional<TicketEntity> ticketTo = Optional.empty();
    if (paymentDTO.getReturnTicket() != null) {
      ticketTo = ticketService.readById(paymentDTO.getReturnTicket().getTicketId());
    }

    if (!ticketFrom.isPresent()) {
      logger.error("Ticket with id: " + paymentDTO.getDepartureTicket().getTicketId() + " does not exists");
      throw new NoSuchElementException("Ticket with id: " +
          paymentDTO.getDepartureTicket().getTicketId() + " does not exists");
    }

    if (ticketFrom.get().getPaid() || (ticketTo.isPresent() && ticketTo.get().getPaid())) {
      logger.error("One of the ticket is alreaty paid");
      throw new RuntimeException("One of the ticket is already paid");
    }

    if (!ticketFrom.get().getPrice().equals(Double.valueOf(paymentDTO.getDepartureTicket().getTicketPrice()))) {
      logger.error("Invalid `departure ticket` price");
      throw new RuntimeException("Invalid `departure ticket` price");
    }
    if (ticketTo.isPresent() && !ticketTo.get().getPrice().equals(Double.valueOf(paymentDTO.getReturnTicket().getTicketPrice()))) {
      logger.error("Invalid `return ticket` to price");
      throw new RuntimeException("Invalid `return ticket` to price");
    }

    if (!ticketFrom.get().getSeats().equals(paymentDTO.getNumberOfPassengers())) {
      logger.error("Invalid ticket to number of passengers");
      throw new RuntimeException("Invalid ticket to number of passengers");
    }

    if (ticketTo.isPresent() && !ticketTo.get().getSeats().equals(paymentDTO.getNumberOfPassengers())) {
      logger.error("Invalid ticket from number of passengers");
      throw new RuntimeException("Invalid ticket from number of passengers");
    }

    List<Product> products = new ArrayList<>();
    products.add(Product.builder()
        .name("Bilet nr: " + ticketFrom.get().getId())
        .unitPrice(convertPriceToSmallestUnit(paymentDTO.getDepartureTicket().getTicketPrice()))
        .quantity(String.valueOf(1))
        .build());

    ticketTo.ifPresent(ticketEntity -> products.add(
        Product.builder()
            .name("Bilet nr: " + ticketEntity.getId())
            .unitPrice(convertPriceToSmallestUnit(paymentDTO.getReturnTicket().getTicketPrice()))
            .quantity(String.valueOf(1))
            .build()
    ));

    String externalOrderId =
        ticketFrom.get().getId().toString() +
        ticketTo.map(ticketEntity -> "," + ticketEntity.getId().toString()).orElse("")
        + ",dev_2";

    return OrderCreateRequest.builder()
        .extOrderId(externalOrderId)
        .notifyUrl(PAYMENTS_NOTIFY_URL)
        .continueUrl(PAYMENTS_CONTINUE_URL)
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
