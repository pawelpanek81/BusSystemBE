package pl.bussystem.domain.ticket.persistence.entity;

import lombok.*;
import pl.bussystem.domain.busride.persistence.entity.BusRideEntity;
import pl.bussystem.domain.user.persistence.entity.AccountEntity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tickets")
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TicketEntity {
  @Id
  @SequenceGenerator(name = "tickets_generator",
      sequenceName = "tickets_id_seq", initialValue = 1)
  @GeneratedValue(generator = "tickets_generator")
  @Column(name = "id", nullable = false)
  private Integer id;

  @ManyToOne
  @JoinColumn(name = "user_account")
  private AccountEntity userAccount;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "surname", nullable = false)
  private String surname;

  @Column(name = "email", nullable = false)
  private String email;

  @Column(name = "phone")
  private String phone;

  @Column(name = "date_time", nullable = false)
  private LocalDateTime dateTime;

  @Column(name = "price", nullable = false)
  private Double price;

  @ManyToOne(optional = false)
  @JoinColumn(name = "bus_ride", nullable = false)
  private BusRideEntity busRide;

  @Column(name = "paid", nullable = false)
  private Boolean paid;

  @Column(name = "returned", nullable = false)
  private Boolean returned;
}
