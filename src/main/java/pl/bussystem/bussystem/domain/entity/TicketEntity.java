package pl.bussystem.bussystem.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tickets")
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class TicketEntity {
  @Id
  @GeneratedValue
  @Column(name = "id", nullable = false)
  private Integer id;

  @ManyToOne(optional = false)
  @JoinColumn(name = "driver", nullable = false)
  private AccountEntity driver;

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
