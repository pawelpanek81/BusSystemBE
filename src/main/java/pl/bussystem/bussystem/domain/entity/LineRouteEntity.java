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
  private Integer id;

  @ManyToOne(optional = false)
  @JoinColumn(name = "bus_line")
  private BusLineEntity busLine;

  @ManyToOne(optional = false)
  @JoinColumn(name = "bus_stop")
  private BusStopEntity busStop;

  @Column(name = "sequence")
  private Integer order;

  @Column(name = "drive_time")
  private Integer driveTime;
}
