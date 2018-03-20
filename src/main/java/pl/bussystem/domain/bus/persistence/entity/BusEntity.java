package pl.bussystem.domain.bus.persistence.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "buses")
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class BusEntity {
  @Id
  @GeneratedValue
  @Column(name = "id", nullable = false)
  private Integer id;

  @Column(name = "registration_number", nullable = false)
  private String registrationNumber;

  @Column(name = "brand", nullable = false)
  private String brand;

  @Column(name = "model", nullable = false)
  private String model;
}
