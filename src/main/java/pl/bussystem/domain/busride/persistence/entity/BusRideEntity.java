package pl.bussystem.domain.busride.persistence.entity;

import lombok.*;
import pl.bussystem.domain.bus.persistence.entity.BusEntity;
import pl.bussystem.domain.lineinfo.busline.persistence.entity.BusLineEntity;
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
@Builder
public class BusRideEntity {
  @Id
  @SequenceGenerator(name = "bus_rides_generator",
      sequenceName = "bus_rides_id_seq", initialValue = 9)
  @GeneratedValue(generator = "bus_rides_generator")
  @Column(name = "id", nullable = false)
  private Integer id;

  @Column(name = "start_date_time", nullable = false)
  private LocalDateTime startDateTime;

  @Column(name = "end_date_time", nullable = false)
  private LocalDateTime endDateTime;

  @ManyToOne(optional = false)
  @JoinColumn(name = "bus_line", nullable = false)
  private BusLineEntity busLine;

  @ManyToOne
  @JoinColumn(name = "primary_driver")
  private AccountEntity primaryDriver;

  @ManyToOne
  @JoinColumn(name = "secondary_driver")
  private AccountEntity secondaryDriver;

  @Column(name = "drive_netto_price", nullable = false)
  private Double driveNettoPrice;

  @ManyToOne
  @JoinColumn(name = "bus")
  private BusEntity bus;

  @Column(name = "active", nullable = false)
  private Boolean active = false;
}
