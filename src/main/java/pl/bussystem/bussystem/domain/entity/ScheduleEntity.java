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
  @Column(name = "id")
  private Integer id;

  @ManyToOne(optional = false)
  @JoinColumn(name = "bus_line")
  private BusLineEntity busLine;

  @Column(name = "code")
  private String code;

  @Column(name = "start_hour")
  private Time startHour;

  @Column(name = "enabled")
  private Boolean enabled;
}
