package pl.bussystem.domain.lineroute.persistence.entity;

import lombok.*;
import pl.bussystem.domain.busline.persistence.entity.BusLineEntity;
import pl.bussystem.domain.busstop.persistence.entity.BusStopEntity;

import javax.persistence.*;

@Entity
@Table(name = "lines_routes", uniqueConstraints={
    @UniqueConstraint(columnNames = {"bus_line", "sequence"})
})
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class LineRouteEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Integer id;

  @ManyToOne(optional = false)
  @JoinColumn(name = "bus_line", nullable = false)
  private BusLineEntity busLine;

  @ManyToOne(optional = false)
  @JoinColumn(name = "bus_stop", nullable = false)
  private BusStopEntity busStop;

  @Column(name = "sequence", nullable = false)
  private Integer order;

  @Column(name = "drive_time", nullable = false)
  private Integer driveTime;
}
