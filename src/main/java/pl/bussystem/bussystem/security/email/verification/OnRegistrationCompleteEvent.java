package pl.bussystem.bussystem.security.email.verification;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;
import pl.bussystem.bussystem.domain.entity.AccountEntity;

import java.util.Locale;

@Getter
@Setter
public class OnRegistrationCompleteEvent extends ApplicationEvent {
  private String appUrl;
  private Locale locale;
  private AccountEntity accountEntity;

  public OnRegistrationCompleteEvent(AccountEntity accountEntity, Locale locale, String appUrl) {
    super(accountEntity);
    this.accountEntity = accountEntity;
    this.locale = locale;
    this.appUrl = appUrl;
  }
}