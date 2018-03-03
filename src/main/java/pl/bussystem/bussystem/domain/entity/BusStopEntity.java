package pl.bussystem.bussystem.domain.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "bus_stops")
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class BusStopEntity {
  @Id
  @GeneratedValue
  @Column(name = "id")
  private Integer id;

  @Column(name = "city")
  private String city;

  @Column(name = "name")
  private String name;
}
