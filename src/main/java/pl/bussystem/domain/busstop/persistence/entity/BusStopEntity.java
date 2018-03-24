package pl.bussystem.domain.busstop.persistence.entity;

import lombok.*;
import pl.bussystem.domain.busstop.model.BusStopDTO;

import javax.persistence.*;
import javax.validation.Valid;

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
  @GeneratedValue(strategy = GenerationType.IDENTITY)
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

  public BusStopEntity(BusStopDTO busStopDTO) {
    this.city = busStopDTO.getCity();
    this.name = busStopDTO.getName();
    this.latitude = busStopDTO.getLatitude();
    this.longitude = busStopDTO.getLongitude();
    this.address = busStopDTO.getAddress();
  }
}
