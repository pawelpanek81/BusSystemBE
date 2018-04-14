package pl.bussystem.domain.user.persistence.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "authorities")
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class AuthorityEntity {
  @Id
  @SequenceGenerator(name = "authorities_generator",
      sequenceName = "authorities_id_seq", initialValue = 8)
  @GeneratedValue(generator = "authorities_generator")
  @Column(name = "id", nullable = false)
  private Integer id;

  @ManyToOne(optional = false)
  @JoinColumn(name = "account", nullable = false)
  private AccountEntity account;

  @Column(name = "authority", nullable = false)
  private String authority;

}
