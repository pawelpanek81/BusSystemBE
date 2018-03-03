package pl.bussystem.bussystem.domain.entity;

import lombok.*;

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
  @GeneratedValue
  @Column(name = "id")
  private Integer id;

  @Column(name = "name")
  private String name;

  @ManyToOne(optional = false)
  @JoinColumn(name = "drive_from")
  private BusStopEntity from;

  @ManyToOne(optional = false)
  @JoinColumn(name = "destination")
  private BusStopEntity to;

  @Column(name = "drive_time")
  private Integer driveTime;
}