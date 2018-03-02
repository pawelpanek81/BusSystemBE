package pl.bussystem.bussystem.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "bus_rides")
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class BusRideEntity {
  @Id
  @GeneratedValue
  @Column(name = "id")
  Integer id;

  @Column(name = "start_date_time")
  LocalDateTime startDateTime;

  @Column(name = "end_date_time")
  LocalDateTime endDateTime;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "bus_line")
  BusLineEntity busLine;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "primary_driver")
  AccountEntity primaryDriver;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "secondary_driver")
  AccountEntity secondaryDriver;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "bus")
  BusEntity bus;
}
