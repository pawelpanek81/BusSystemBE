package pl.bussystem.domain.busline.schedule.persistence.entity;

import lombok.*;
import pl.bussystem.domain.busline.busline.persistence.entity.BusLineEntity;

import javax.persistence.*;
import java.sql.Time;

@Entity
@Table(name = "schedules")
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScheduleEntity {
  @Id
  @SequenceGenerator(name = "schedules_generator",
      sequenceName = "schedules_id_seq", initialValue = 17)
  @GeneratedValue(generator = "schedules_generator")
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
