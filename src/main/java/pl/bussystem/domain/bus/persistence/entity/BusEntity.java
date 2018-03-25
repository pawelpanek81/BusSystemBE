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
@Builder
public class BusEntity {
  @Id
  @SequenceGenerator(name = "buses_generator",
      sequenceName = "buses_id_seq", initialValue = 1)
  @GeneratedValue(generator = "buses_generator")
  @Column(name = "id", nullable = false)
  private Integer id;

  @Column(name = "registration_number", nullable = false, unique = true)
  private String registrationNumber;

  @Column(name = "brand", nullable = false)
  private String brand;

  @Column(name = "model", nullable = false)
  private String model;

  @Column(name = "seats", nullable = false)
  private Integer seats;
}
