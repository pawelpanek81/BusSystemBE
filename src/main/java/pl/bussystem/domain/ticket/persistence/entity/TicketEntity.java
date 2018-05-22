package pl.bussystem.domain.ticket.persistence.entity;

import lombok.*;
import pl.bussystem.domain.busride.persistence.entity.BusRideEntity;
import pl.bussystem.domain.busstop.persistence.entity.BusStopEntity;
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

  @Column(name = "seats", nullable = false)
  private Integer seats;

  @ManyToOne(optional = false)
  @JoinColumn(name = "bus_ride", nullable = false)
  private BusRideEntity busRide;

  @ManyToOne(optional = false)
  @JoinColumn(name = "from_bus_stop_id")
  private BusStopEntity fromBusStop;

  @ManyToOne(optional = false)
  @JoinColumn(name = "dest_bus_stop_id")
  private BusStopEntity destBusStop;

  @Column(name = "paid", nullable = false)
  private Boolean paid;
}
