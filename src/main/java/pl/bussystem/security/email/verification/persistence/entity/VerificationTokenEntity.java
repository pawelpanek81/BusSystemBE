package pl.bussystem.security.email.verification.persistence.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.bussystem.domain.user.persistence.entity.AccountEntity;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

@Entity
@Table(name = "VerificationTokens")
@Getter
@Setter
@NoArgsConstructor
public class VerificationTokenEntity {
  private static final int EXPIRATION = 60 * 24;

  @Id
  @SequenceGenerator(name = "verification_token_generator",
      sequenceName = "verification_token_id_seq", initialValue = 1)
  @GeneratedValue(generator = "verification_token_generator")
  @Column(name = "id")
  private Long id;

  @Column(name = "token")
  private String token;

  @OneToOne(targetEntity = AccountEntity.class, fetch = FetchType.EAGER)
  @JoinColumn(nullable = false, name = "account_id", unique = true)
  private AccountEntity accountEntity;

  @Column(name = "expiry_date")
  private Date expiryDate;

  public VerificationTokenEntity(final String token, final AccountEntity accountEntity) {
    super();
    this.token = token;
    this.accountEntity = accountEntity;
    this.expiryDate = calculateExpiryDate(EXPIRATION);
  }

  private Date calculateExpiryDate(final int expiryTimeInMinutes) {
    final Calendar cal = Calendar.getInstance();
    cal.setTimeInMillis(new Date().getTime());
    cal.add(Calendar.MINUTE, expiryTimeInMinutes);
    return new Date(cal.getTime().getTime());
  }
}