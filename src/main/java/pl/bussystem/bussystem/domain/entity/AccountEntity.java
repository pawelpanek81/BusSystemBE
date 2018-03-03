package pl.bussystem.bussystem.domain.entity;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "accounts")
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class AccountEntity {
  @Id
  @GeneratedValue
  @Column(name = "id")
  private Integer id;

  @Column(name = "username")
  private String username;

  @Column(name = "name")
  private String name;

  @Column(name = "surname")
  private String surname;

  @Column(name = "password")
  private String password;

  @Column(name = "email")
  private String email;

  @Column(name = "phone")
  private String phone;

  @Column(name = "active")
  private Boolean active;

  @OneToMany(mappedBy = "account",
      cascade = CascadeType.ALL,
      orphanRemoval = true,
      fetch = FetchType.EAGER)
  private List<AuthorityEntity> authorities;

  @Column(name = "photo")
  private String photo;

  public void addAuthority(AuthorityEntity authorityEntity) {
    this.authorities.add(authorityEntity);
    if (authorityEntity.getAccount() != this) {
      authorityEntity.setAccount(this);
    }
  }
}
