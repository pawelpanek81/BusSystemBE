package pl.bussystem.bussystem.security.email.verification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import pl.bussystem.bussystem.domain.entity.AccountEntity;
import pl.bussystem.bussystem.security.email.verification.service.VerificationTokenService;

import java.util.UUID;

@Component
public class RegistrationListener implements
    ApplicationListener<OnRegistrationCompleteEvent> {

  private VerificationTokenService verificationTokenService;
  private MessageSource messages;
  private JavaMailSender mailSender;
  private Environment env;

  @Autowired
  public RegistrationListener(VerificationTokenService verificationTokenService,
                              @Qualifier("messageSource") MessageSource messages,
                              JavaMailSender mailSender,
                              Environment env) {
    this.verificationTokenService = verificationTokenService;
    this.messages = messages;
    this.mailSender = mailSender;
    this.env = env;
  }

  @Override
  public void onApplicationEvent(OnRegistrationCompleteEvent event) {
    this.confirmRegistration(event);
  }

  private void confirmRegistration(final OnRegistrationCompleteEvent event) {
    final AccountEntity user = event.getAccountEntity();
    final String token = UUID.randomUUID().toString();
    verificationTokenService.createVerificationTokenForUser(user, token);

    final SimpleMailMessage email = constructEmailMessage(event, user, token);
    mailSender.send(email);
  }


  private final SimpleMailMessage constructEmailMessage(final OnRegistrationCompleteEvent event,
                                                        final AccountEntity user,
                                                        final String token) {
    final String recipientAddress = user.getEmail();

    final String subject = messages.getMessage("message.registerMailSubject", null, event.getLocale());

    final String confirmationUrl = event.getAppUrl() + env.getProperty("message.confirmationUrl") + token;

    final String message = messages.getMessage("message.registerMailBody", null, event.getLocale());

    final SimpleMailMessage email = new SimpleMailMessage();
    email.setTo(recipientAddress);
    email.setSubject(subject);
    email.setText(message + " \r\n" + confirmationUrl);
    email.setFrom(env.getProperty("support.email"));
    return email;
  }
}
