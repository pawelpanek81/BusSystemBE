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
  Integer id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "driver")
  AccountEntity driver;

  @Column(name = "name")
  String name;

  @Column(name = "surname")
  String surname;

  @Column(name = "email")
  String email;

  @Column(name = "phone")
  String phone;

  @Column(name = "date_time")
  LocalDateTime dateTime;

  @Column(name = "price")
  Double price;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "bus_ride")
  BusRideEntity busRide;

  @Column(name = "paid")
  Boolean paid;

  @Column(name = "returned")
  Boolean returned;
}
