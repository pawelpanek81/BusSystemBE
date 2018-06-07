package pl.bussystem.domain.ticket.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;
import io.nayuki.qrcodegen.QrCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import pl.bussystem.domain.busstop.persistence.entity.BusStopEntity;
import pl.bussystem.domain.ticket.exception.NoSuchTicketException;
import pl.bussystem.domain.ticket.exception.QRCodeGenerationFailedException;
import pl.bussystem.domain.ticket.persistence.entity.TicketEntity;
import pl.bussystem.domain.ticket.persistence.repository.TicketRepository;
import pl.bussystem.domain.user.service.AccountService;
import pl.bussystem.email.EmailSender;
import pl.bussystem.security.payment.persistence.entity.OrderEntity;
import pl.bussystem.security.payment.persistence.repository.OrderRepository;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TicketServiceImpl implements TicketService {
  private static final int FIVE_MINUTES_IN_MILLISECONDS = 1000 * 60 * 5;
  private TicketRepository ticketRepository;
  private OrderRepository orderRepository;
  private AccountService accountService;
  private final EmailSender emailSender;
  private final TemplateEngine templateEngine;
  private static final Logger logger = LoggerFactory.getLogger(TicketServiceImpl.class);

  @Autowired
  public TicketServiceImpl(TicketRepository ticketRepository,
                           OrderRepository orderRepository,
                           AccountService accountService,
                           EmailSender emailSender,
                           TemplateEngine templateEngine) {
    this.ticketRepository = ticketRepository;
    this.orderRepository = orderRepository;
    this.accountService = accountService;
    this.emailSender = emailSender;
    this.templateEngine = templateEngine;
  }

  @Override
  public TicketEntity create(TicketEntity ticketEntity) {
    return ticketRepository.save(ticketEntity);
  }

  @Override
  public List<TicketEntity> read() {
    return ticketRepository.findAll();
  }

  @Override
  public List<TicketEntity> readUserTickets(Principal principal) {
    return this.read().stream()
        .filter(ticketEntity -> ticketEntity.getUserAccount() != null)
        .filter(ticketEntity -> ticketEntity.getUserAccount().equals(accountService.findAccountByPrincipal(principal)))
        .filter(ticketEntity -> ticketEntity.getPaid().equals(true))
        .collect(Collectors.toList());
  }

  @Override
  public Optional<TicketEntity> readById(Integer id) {
    return ticketRepository.findById(id);
  }

  @Override
  public void makeTicketPaid(Integer ticketId) {
    Optional<TicketEntity> byId = ticketRepository.findById(ticketId);
    TicketEntity ticketEntity = byId.orElseThrow(
        () -> new NoSuchElementException("There is no ticket with id: " + ticketId)
    );
    if (ticketEntity.getPaid()) {
      throw new IllegalArgumentException("Thicket with id: " + ticketId + " is already paid");
    }
    ticketEntity.setPaid(true);
    ticketRepository.save(ticketEntity);
  }

  @Scheduled(fixedRate = FIVE_MINUTES_IN_MILLISECONDS)
  public void removeNotPayedTickets() {
    logger.info("Removing not payed tickets " + LocalDateTime.now() + "...");
    List<TicketEntity> tickets = ticketRepository.findAllByPaid(Boolean.FALSE);
    List<OrderEntity> orders = orderRepository.findAll();
    List<Integer> removedTickets = new ArrayList<>();

    for (TicketEntity ticket : tickets) {
      if (LocalDateTime.now().isAfter(ticket.getDateTime().plusHours(1))) {
        ticketRepository.delete(ticket);
        removedTickets.add(ticket.getId());
      }
    }

    logger.info("Removing not payed orders " + LocalDateTime.now() + "...");
    for (OrderEntity order : orders) {
      String[] splittedId = order.getOrderId().split(",");

      if (removedTickets.contains(Integer.valueOf(splittedId[0]))) {
        orderRepository.delete(order);
      }
    }
  }


  private String[] extractTicketData(Integer ticketId) throws NoSuchTicketException {
    Optional<TicketEntity> optionalTicket = this.readById(ticketId);
    if (!optionalTicket.isPresent()) {
      throw new NoSuchTicketException("There is no ticket with given id");
    }
    TicketEntity ticket = optionalTicket.get();

    BusStopEntity from = ticket.getFromBusStop();
    BusStopEntity dest = ticket.getDestBusStop();
    String owner = ticket.getName() + " " + ticket.getSurname();
    String route = from.getCity() + " - " + from.getName()
        + " -> " + dest.getCity() + " - " + dest.getName();
    String paid = ticket.getPaid() ? "Opłacony" : "Nieopłacony";

    return new String[]{owner, route, paid};
  }

  @Override
  public byte[] generateQRCode(Integer ticketId) throws NoSuchTicketException, QRCodeGenerationFailedException {
    String[] ticketData = extractTicketData(ticketId);
    String owner = ticketData[0];
    String route = ticketData[1];
    String paid = ticketData[2];

    PasswordEncoder encoder = new BCryptPasswordEncoder();
    String payload = encoder.encode(owner + route + ticketId + paid);

    String data = "Właściciel biletu: " + owner
        + "\n" + "Trasa: " + route
        + "\n" + "Id: " + ticketId
        + "\n" + "Status pałtności: " + paid
        + "\n" + "Payload: " + payload;

    QrCode qr0 = QrCode.encodeText(data, QrCode.Ecc.MEDIUM);
    BufferedImage img = qr0.toImage(4, 10);
    byte[] bytes;
    try {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      ImageIO.write(img, "jpg", baos);
      bytes = baos.toByteArray();
    } catch (IOException exc) {
      logger.error("Saving QR code failed");
      throw new QRCodeGenerationFailedException("Failed to save generated qr-code");
    }
    return bytes;
  }

  @Override
  public boolean verifyTicket(String owner, String route, Integer ticketId,
                              String paymentStatus, String payload) throws NoSuchTicketException {

    String[] ticketData = extractTicketData(ticketId);
    String realOwner = ticketData[0];
    String realRoute = ticketData[1];
    String reaPaid = ticketData[2];

    PasswordEncoder encoder = new BCryptPasswordEncoder();
    return realOwner.equals(owner)
        && realRoute.equals(route)
        && reaPaid.equals(paymentStatus)
        && encoder.matches(realOwner + realRoute + ticketId + reaPaid, payload);

  }

  public ByteArrayOutputStream makePDF(Integer id) {
    Optional<TicketEntity> optionalTicket = this.readById(id);
    if (!optionalTicket.isPresent()) {
      throw new NoSuchTicketException("There is no ticket with given id");
    }
    TicketEntity ticket = optionalTicket.get();

    ByteArrayOutputStream os = new ByteArrayOutputStream();
    Document document = new Document();

    try {

      Font regular = FontFactory.getFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.EMBEDDED, 12);
      Font bold = FontFactory.getFont(BaseFont.HELVETICA_BOLD, BaseFont.CP1250, BaseFont.EMBEDDED, 12);

      PdfWriter writer = PdfWriter.getInstance(document, os);
      document.open();

      document.addAuthor("JanuszPol");
      document.addCreationDate();
      document.addCreator("JanuszPol System");
      document.addTitle("Bilet nr: " + id);
      document.addSubject("Bilet PDF");

      {
        Paragraph p = new Paragraph("Bilet nr: ", bold);
        p.add(new Chunk(ticket.getId().toString(), regular));
        document.add(p);
      }


      {
        Paragraph p = new Paragraph("Data kupienia: ", bold);
        p.add(new Chunk(ticket.getDateTime().toString(), regular));
        document.add(p);
      }


      {
        String startBusStop = ticket.getFromBusStop().getCity() + " " + ticket.getFromBusStop().getName();
        Paragraph p = new Paragraph("Przystanek początkowy: ", bold);
        p.add(new Chunk(startBusStop, regular));
        document.add(p);
      }


      {
        String stopBusStop = ticket.getDestBusStop().getCity() + " " + ticket.getDestBusStop().getName();
        Paragraph p = new Paragraph("Przystanek końcowy: ", bold);
        p.add(new Chunk(stopBusStop, regular));
        document.add(p);
      }

      document.add(new Paragraph("\n"));

      {
        document.add(new Paragraph("Dane podróżującego: ", bold));
        com.itextpdf.text.List unorderedList = new com.itextpdf.text.List(com.itextpdf.text.List.UNORDERED);

        ListItem name = new ListItem("Imię: ", bold);
        name.add(new Chunk(ticket.getName(), regular));
        unorderedList.add(name);

        ListItem surname = new ListItem("Nazwisko: ", bold);
        surname.add(new Chunk(ticket.getSurname(), regular));
        unorderedList.add(surname);


        ListItem phone = new ListItem("Telefon: ", bold);
        phone.add(new Chunk(ticket.getPhone(), regular));
        unorderedList.add(phone);

        ListItem email = new ListItem("Email: ", bold);
        email.add(new Chunk(ticket.getEmail(), regular));
        unorderedList.add(email);

        document.add(unorderedList);
      }

      document.add(new Paragraph("\n"));

      {
        String paid = ticket.getPaid() ? "Opłacony" : "Nieopłacony";
        Paragraph p = new Paragraph("Status: ", bold);
        p.add(new Chunk(paid, regular));
        document.add(p);
      }


      {
        Paragraph p = new Paragraph("Cena: ", bold);
        p.add(new Chunk(ticket.getPrice().toString(), regular));
        document.add(p);
      }

      {
        Paragraph p = new Paragraph("Liczba miejsc: ", bold);
        p.add(new Chunk(ticket.getSeats().toString(), regular));
        document.add(p);
      }

      {
        Paragraph p = new Paragraph("Wyjazd: ", bold);
        p.add(new Chunk(ticket.getBusRide().getStartDateTime().toString(), regular));
        document.add(p);
      }

      {
        Paragraph p = new Paragraph("Przyjazd: ", bold);
        p.add(new Chunk(ticket.getBusRide().getEndDateTime().toString(), regular));
        document.add(p);
      }

      document.add(new Paragraph("\n"));

      {
        Paragraph p = new Paragraph("Kod QR: ", bold);
        p.add(new Chunk("(do okazania kierowcy)", regular));
        document.add(p);
        byte[] img = this.generateQRCode(id);
        document.add(new Jpeg(img));
      }

      Font ffont = FontFactory.getFont(BaseFont.TIMES_ITALIC, BaseFont.CP1250, BaseFont.EMBEDDED, 10);
      PdfContentByte cb = writer.getDirectContent();
      Phrase header = new Phrase("JanuszPol sp. z.o.o. Janusz i Grażyna Nosacze", ffont);
      Phrase footer = new Phrase("Dziekujemy za skorzystanie z naszych usług i zapraszamy ponownie :-)", ffont);
      ColumnText.showTextAligned(cb, Element.ALIGN_CENTER,
          header,
          (document.right() - document.left()) / 2 + document.leftMargin(),
          document.top() + 10, 0);
      ColumnText.showTextAligned(cb, Element.ALIGN_CENTER,
          footer,
          (document.right() - document.left()) / 2 + document.leftMargin(),
          document.bottom() - 10, 0);

      document.close();
      writer.close();
    } catch (DocumentException | QRCodeGenerationFailedException | IOException e) {
      e.printStackTrace();
    }

    return os;

  }

  public void sendTicketMail(Integer id) {
    Optional<TicketEntity> optionalTicket = this.readById(id);
    if (!optionalTicket.isPresent()) {
      throw new NoSuchTicketException("There is no ticket with given id");
    }
    TicketEntity ticket = optionalTicket.get();

    Context context = new Context();
    context.setVariable("ticketUrl", "http://www.januszpol-rest.herokuapp.com/api/v1.0/tickets/");
    String body = templateEngine.process("sendTicket", context);

    emailSender.sendEmail(ticket.getEmail(), "Bilet :: JanuszPol", body,
        "bilet.pdf", new ByteArrayResource(this.makePDF(id).toByteArray()), MediaType.APPLICATION_PDF_VALUE);
  }
}