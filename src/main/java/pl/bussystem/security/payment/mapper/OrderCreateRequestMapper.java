package pl.bussystem.security.payment.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import pl.bussystem.domain.ticket.persistence.entity.TicketEntity;
import pl.bussystem.domain.ticket.service.TicketService;
import pl.bussystem.security.payment.model.dto.PaymentDTO;
import pl.bussystem.security.payment.model.dto.TicketDTO;
import pl.bussystem.security.payment.model.payu.common.Buyer;
import pl.bussystem.security.payment.model.payu.common.Product;
import pl.bussystem.security.payment.model.payu.orders.create.request.OrderCreateRequest;
import pl.bussystem.security.payment.model.payu.orders.create.request.Settings;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Component
public class OrderCreateRequestMapper {
  @Value("${payu.pos_id}")
  private String merchantPosID;
  private TicketService ticketService;

  @Autowired
  public OrderCreateRequestMapper(TicketService ticketService) {
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
        .quantity("1")
        .build());

    ticketTo.ifPresent(ticketEntity -> products.add(
        Product.builder()
            .name("Bilet nr: " + ticketEntity.getId())
            .unitPrice(paymentDTO.getToTicket().getTicketPrice())
            .quantity("1")
            .build()
    ));

    return OrderCreateRequest.builder()
        .notifyUrl("http://januszpol-rest.herokuapp.com/api/v1.0/payments/notify")
        .customerIp(req.getRemoteAddr())
        .merchantPosId(merchantPosID)
        .description("Sprzedaż biletu")
        .currencyCode("PLN")
        .totalAmount(this.getTotalAmount(paymentDTO))
        .buyer(
            Buyer.builder()
                .email(ticketFrom.get().getEmail())
                .phone(ticketFrom.get().getPhone())
                .firstName(ticketFrom.get().getName())
                .lastName(ticketFrom.get().getSurname())
                .language("pl")
                .build())
        .settings(
            Settings.builder()
                .invoiceDisabled(Boolean.TRUE.toString())
                .build())
        .products(products)
        .build();
  }
}
