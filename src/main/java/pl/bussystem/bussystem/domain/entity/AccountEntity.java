package pl.bussystem.bussystem.domain.entity;


import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "accounts")
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class AccountEntity {
  @Id
  @GeneratedValue
  @Column(name = "id")
  Integer id;

  @Column(name = "username")
  String username;

  @Column(name = "name")
  String name;

  @Column(name = "surname")
  String surname;

  @Column(name = "password")
  String passwordHash;

  @Column(name = "email")
  String email;

  @Column(name = "phone")
  String phone;

  @Column(name = "active")
  Boolean active;

  @Column(name = "acc_type")
  String accType;

  @Column(name = "photo")
  String photo;
}
