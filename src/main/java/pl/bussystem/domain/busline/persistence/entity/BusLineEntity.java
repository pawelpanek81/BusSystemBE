package pl.bussystem.domain.busline.persistence.entity;

import lombok.*;
import pl.bussystem.domain.busstop.persistence.entity.BusStopEntity;

import javax.persistence.*;

@Entity
@Table(name = "bus_lines")
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class BusLineEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Integer id;

  @Column(name = "name", nullable = false)
  private String name;

  @ManyToOne(optional = false)
  @JoinColumn(name = "drive_from", nullable = false)
  private BusStopEntity from;

  @ManyToOne(optional = false)
  @JoinColumn(name = "destination", nullable = false)
  private BusStopEntity to;

  @Column(name = "drive_time", nullable = false)
  private Integer driveTime;
}