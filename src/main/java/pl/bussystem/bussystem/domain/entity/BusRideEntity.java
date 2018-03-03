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
  private Integer id;

  @Column(name = "start_date_time")
  private LocalDateTime startDateTime;

  @Column(name = "end_date_time")
  private LocalDateTime endDateTime;

  @ManyToOne(optional = false)
  @JoinColumn(name = "bus_line")
  private BusLineEntity busLine;

  @ManyToOne(optional = false)
  @JoinColumn(name = "primary_driver")
  private AccountEntity primaryDriver;

  @ManyToOne(optional = false)
  @JoinColumn(name = "secondary_driver")
  private AccountEntity secondaryDriver;

  @ManyToOne(optional = false)
  @JoinColumn(name = "bus")
  private BusEntity bus;
}
