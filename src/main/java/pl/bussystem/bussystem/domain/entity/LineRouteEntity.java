package pl.bussystem.bussystem.domain.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "lines_routes")
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class LineRouteEntity {
  @Id
  @GeneratedValue
  @Column(name = "id")
  Integer id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "bus_line")
  BusLineEntity busLine;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "bus_stop")
  BusStopEntity busStop;

  @Column(name = "sequence")
  Integer order;

  @Column(name = "drive_time")
  Integer driveTime;
}
