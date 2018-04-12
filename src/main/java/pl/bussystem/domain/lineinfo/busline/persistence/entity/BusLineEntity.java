package pl.bussystem.domain.lineinfo.busline.persistence.entity;

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
@Builder
public class BusLineEntity {
  @Id
  @SequenceGenerator(name = "bus_lines_generator",
      sequenceName = "bus_lines_id_seq", initialValue = 17)
  @GeneratedValue(generator = "bus_lines_generator")
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