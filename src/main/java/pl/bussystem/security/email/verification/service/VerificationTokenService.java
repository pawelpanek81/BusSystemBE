package pl.bussystem.security.email.verification.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import pl.bussystem.domain.user.persistence.entity.AccountEntity;
import pl.bussystem.domain.user.persistence.repository.AccountRepository;
import pl.bussystem.security.email.verification.persistence.entity.VerificationTokenEntity;
import pl.bussystem.security.email.verification.persistence.repository.VerificationTokenRepository;

import java.util.Calendar;

@Service
public class VerificationTokenService {
  public static final String TOKEN_INVALID = "invalidToken";
  public static final String TOKEN_EXPIRED = "expired";
  public static final String TOKEN_VALID = "valid";

  private VerificationTokenRepository verificationTokenRepository;
  private AccountRepository accountRepository;

  @Autowired
  public VerificationTokenService(VerificationTokenRepository verificationTokenRepository,
                                  AccountRepository accountRepository) {
    this.verificationTokenRepository = verificationTokenRepository;
    this.accountRepository = accountRepository;
  }

  public void createVerificationTokenForUser(AccountEntity user, String token) {
    final VerificationTokenEntity myToken = new VerificationTokenEntity(token, user);
    verificationTokenRepository.save(myToken);
  }

  public String validateVerificationToken(String token) {
    final VerificationTokenEntity verificationToken = verificationTokenRepository.findByToken(token);
    if (verificationToken == null) {
      return TOKEN_INVALID;
    }

    final AccountEntity user = verificationToken.getAccountEntity();
    final Calendar cal = Calendar.getInstance();
    if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
      verificationTokenRepository.delete(verificationToken);
      return TOKEN_EXPIRED;
    }

    user.setActive(true);
    verificationTokenRepository.delete(verificationToken);
    accountRepository.save(user);
    return TOKEN_VALID;
  }
}
