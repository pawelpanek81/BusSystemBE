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
  Integer id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "bus_line")
  BusLineEntity busLine;

  @Column(name = "code")
  String code;

  @Column(name = "start_hour")
  Time startHour;

  @Column(name = "enabled")
  Boolean enabled;
}
