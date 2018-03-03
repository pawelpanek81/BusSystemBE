package pl.bussystem.bussystem.domain.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "authorities")
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class AuthorityEntity {
  @Id
  @GeneratedValue
  @Column(name = "id", nullable = false)
  private Integer id;

  @ManyToOne(optional = false)
  @JoinColumn(name = "account", nullable = false)
  private AccountEntity account;

  @Column(name = "authority", nullable = false)
  private String authority;

  public void setAuthority(String authority) {
    this.authority = authority;
    if (!account.getAuthorities().contains(this)) {
      account.getAuthorities().add(this);
    }
  }
}
