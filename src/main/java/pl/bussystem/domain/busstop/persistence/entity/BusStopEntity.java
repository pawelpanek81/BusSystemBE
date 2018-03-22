package pl.bussystem.domain.busstop.persistence.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "bus_stops")
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BusStopEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Integer id;

  @Column(name = "city", nullable = false)
  private String city;

  @Column(name = "name", nullable = false)
  private String name;
}
