package pl.bussystem.bussystem.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.sql.Time;

@Entity
@Table(name = "schedules")
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleEntity {
  @Id
  @GeneratedValue
  @Column(name = "id", nullable = false)
  private Integer id;

  @ManyToOne(optional = false)
  @JoinColumn(name = "bus_line", nullable = false)
  private BusLineEntity busLine;

  @Column(name = "code", nullable = false)
  private String code;

  @Column(name = "start_hour", nullable = false)
  private Time startHour;

  @Column(name = "enabled", nullable = false)
  private Boolean enabled;
}
