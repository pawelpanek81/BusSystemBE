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
  @Column(name = "id")
  private Integer id;

  @ManyToOne(optional = false)
  @JoinColumn(name = "driver")
  private AccountEntity driver;

  @Column(name = "name")
  private String name;

  @Column(name = "surname")
  private String surname;

  @Column(name = "email")
  private String email;

  @Column(name = "phone")
  private String phone;

  @Column(name = "date_time")
  private LocalDateTime dateTime;

  @Column(name = "price")
  private Double price;

  @ManyToOne(optional = false)
  @JoinColumn(name = "bus_ride")
  private BusRideEntity busRide;

  @Column(name = "paid")
  private Boolean paid;

  @Column(name = "returned")
  private Boolean returned;
}
