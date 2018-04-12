package pl.bussystem.domain.user.persistence.entity;


import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "accounts")
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@Builder
public class AccountEntity {

  public AccountEntity() {
    super();
    this.active = false;
  }
  @Id
  @SequenceGenerator(name = "accounts_generator",
      sequenceName = "accounts_id_seq", initialValue = 8)
  @GeneratedValue(generator = "accounts_generator")
  @Column(name = "id", nullable = false)
  private Integer id;

  @Column(name = "username", nullable = false, unique = true)
  private String username;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "surname", nullable = false)
  private String surname;

  @Column(name = "password", nullable = false)
  private String password;

  @Column(name = "email", nullable = false, unique = true)
  private String email;

  @Column(name = "phone")
  private String phone;

  @Column(name = "active", nullable = false)
  private Boolean active;

  @Column(name = "photo")
  private String photo;
}
