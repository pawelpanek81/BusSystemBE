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
  Integer id;

  @Column(name = "registration_number")
  String registrationNumber;

  @Column(name = "brand")
  String brand;

  @Column(name = "model")
  String model;
}
