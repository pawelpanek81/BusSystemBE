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
  Integer id;

  @Column(name = "city")
  String city;

  @Column(name = "name")
  String name;
}
