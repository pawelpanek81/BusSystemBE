package pl.bussystem.domain.busstop.persistence.entity;

import lombok.*;
import pl.bussystem.domain.busstop.model.dto.CreateBusStopDTO;

import javax.persistence.*;

@Entity
@Table(name = "bus_stops", uniqueConstraints = { @UniqueConstraint(columnNames = {"city", "name"})})
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BusStopEntity {
  @Id
  @SequenceGenerator(name = "bus_stops_generator",
      sequenceName = "bus_stops_id_seq", initialValue = 14)
  @GeneratedValue(generator = "bus_stops_generator")
  @Column(name = "id", nullable = false)
  private Integer id;

  @Column(name = "city", nullable = false)
  private String city;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "latitude", nullable = false)
  private String latitude;

  @Column(name = "longitude", nullable = false)
  private String longitude;

  @Column(name = "address")
  private String address;

}
