package pl.bussystem.security.payment.persistence.entity;

import lombok.*;
import pl.bussystem.domain.ticket.persistence.entity.TicketEntity;

import javax.persistence.*;

@Entity
@Table(name = "orders")
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class OrderEntity {
  @Id
  @Column(name = "order_id", nullable = false)
  private String orderId;

  @Column(name = "url", nullable = false, length = 5000)
  private String URL;

  @ManyToOne(optional = false)
  @JoinColumn(name = "first_ticket")
  private TicketEntity firstTicket;

  @ManyToOne()
  @JoinColumn(name = "second_ticket")
  private TicketEntity secondTicket;
}