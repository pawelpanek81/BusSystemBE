package pl.bussystem.domain.busride.persistence.entity;

import lombok.*;
import pl.bussystem.domain.bus.persistence.entity.BusEntity;
import pl.bussystem.domain.busline.persistence.entity.BusLineEntity;
import pl.bussystem.domain.user.persistence.entity.AccountEntity;

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
  @Column(name = "id", nullable = false)
  private Integer id;

  @Column(name = "start_date_time", nullable = false)
  private LocalDateTime startDateTime;

  @Column(name = "end_date_time", nullable = false)
  private LocalDateTime endDateTime;

  @ManyToOne(optional = false)
  @JoinColumn(name = "bus_line", nullable = false)
  private BusLineEntity busLine;

  @ManyToOne(optional = false)
  @JoinColumn(name = "primary_driver", nullable = false)
  private AccountEntity primaryDriver;

  @ManyToOne(optional = false)
  @JoinColumn(name = "secondary_driver")
  private AccountEntity secondaryDriver;

  @ManyToOne(optional = false)
  @JoinColumn(name = "bus", nullable = false)
  private BusEntity bus;
}