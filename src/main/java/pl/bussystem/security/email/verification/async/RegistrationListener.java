package pl.bussystem.security.email.verification.async;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import pl.bussystem.domain.user.persistence.entity.AccountEntity;
import pl.bussystem.security.email.verification.service.SendEmailService;
import pl.bussystem.security.email.verification.service.VerificationTokenService;

import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class RegistrationListener implements
    ApplicationListener<RegistrationCompleteEvent> {

  private VerificationTokenService verificationTokenService;
  private MessageSource messages;
  private Environment env;
  private SendEmailService sendEmailService;

  @Autowired
  public RegistrationListener(VerificationTokenService verificationTokenService,
                              MessageSource messages,
                              Environment env,
                              SendEmailService sendEmailService) {
    this.verificationTokenService = verificationTokenService;
    this.messages = messages;
    this.env = env;
    this.sendEmailService = sendEmailService;
  }

  @Override
  public void onApplicationEvent(RegistrationCompleteEvent event) {
    this.confirmRegistration(event);
  }

  private void confirmRegistration(RegistrationCompleteEvent event) {
    AccountEntity user = event.getAccountEntity();
    String token = UUID.randomUUID().toString();
    verificationTokenService.createVerificationTokenForUser(user, token);

    MimeMessage email = constructVerificationTokenEmail(event, user, token);
    sendEmailService.sendEmail(email);
  }


  private MimeMessage constructVerificationTokenEmail(RegistrationCompleteEvent event,
                                                      AccountEntity user,
                                                      String token) {
    String recipientAddress = user.getEmail();
    String subject = messages.getMessage("message.registerMailSubject", null, event.getLocale());
    String confirmationUrl = event.getAppUrl() + env.getProperty("message.confirmationUrl") + token;
    String message = messages.getMessage("message.registerMailBody", null, event.getLocale());

    String preheader = messages.getMessage("message.preheader", null, event.getLocale());
    String welcome = messages.getMessage("message.welcome", null, event.getLocale());
    String registerButtonText = messages.getMessage("message.registerButtonText", null, event.getLocale());
    String alternativeUrlPrefix = messages.getMessage("message.alternativeUrlPrefix", null, event.getLocale());
    String emailLastText = messages.getMessage("message.emailLastText", null, event.getLocale());
    String footerText = messages.getMessage("message.footerText", null, event.getLocale());

    List<String> templateArgs = new ArrayList<>();
    templateArgs.add(preheader);
    templateArgs.add(welcome);
    templateArgs.add(user.getName());
    templateArgs.add(message);
    templateArgs.add(confirmationUrl);
    templateArgs.add(registerButtonText);
    templateArgs.add(alternativeUrlPrefix + confirmationUrl);
    templateArgs.add(emailLastText);
    templateArgs.add(footerText);

    return sendEmailService.constructEmailMessage(
        "static/mailtemplate.html",
        templateArgs,
        recipientAddress,
        subject
    );

  }
}
