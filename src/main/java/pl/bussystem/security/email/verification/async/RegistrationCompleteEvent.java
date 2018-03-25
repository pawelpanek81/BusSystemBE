package pl.bussystem.security.email.verification.async;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;
import pl.bussystem.domain.user.persistence.entity.AccountEntity;

import java.util.Locale;

@Getter
@Setter
public class RegistrationCompleteEvent extends ApplicationEvent {
  private String appUrl;
  private Locale locale;
  private AccountEntity accountEntity;

  public RegistrationCompleteEvent(AccountEntity accountEntity, Locale locale, String appUrl) {
    super(accountEntity);
    this.accountEntity = accountEntity;
    this.locale = locale;
    this.appUrl = appUrl;
  }
}