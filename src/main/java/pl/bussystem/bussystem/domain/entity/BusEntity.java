package pl.bussystem.bussystem.domain.entity;

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
  @Column(name = "id")
  private Integer id;

  @Column(name = "registration_number")
  private String registrationNumber;

  @Column(name = "brand")
  private String brand;

  @Column(name = "model")
  private String model;
}
