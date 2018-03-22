package pl.bussystem.security.email.verification.async;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import pl.bussystem.domain.user.persistence.entity.AccountEntity;
import pl.bussystem.security.email.verification.service.VerificationTokenService;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.Objects;
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

    final MimeMessage email = constructEmailMessage(event, user, token);
    mailSender.send(email);
  }


  private final MimeMessage constructEmailMessage(final OnRegistrationCompleteEvent event,
                                                  final AccountEntity user,
                                                  final String token) {
    final String recipientAddress = user.getEmail();
    final String subject = messages.getMessage("message.registerMailSubject", null, event.getLocale());
    final String confirmationUrl = event.getAppUrl() + env.getProperty("message.confirmationUrl") + token;
    final String message = messages.getMessage("message.registerMailBody", null, event.getLocale());

    final String preheader = messages.getMessage("message.preheader", null, event.getLocale());
    final String welcome = messages.getMessage("message.welcome", null, event.getLocale());
    final String registerButtonText = messages.getMessage("message.registerButtonText", null, event.getLocale());
    final String alternativeUrlPrefix = messages.getMessage("message.alternativeUrlPrefix", null, event.getLocale());
    final String emailLastText = messages.getMessage("message.emailLastText", null, event.getLocale());
    final String footerText = messages.getMessage("message.footerText", null, event.getLocale());

    MimeMessage mimeMessage = mailSender.createMimeMessage();
    MimeMessageHelper helper = null;
    try {
      helper = new MimeMessageHelper(mimeMessage, false, "UTF-8");

      ClassLoader classLoader = getClass().getClassLoader();
      String filePath = Objects.requireNonNull(classLoader.getResource("static/mailtemplate.html")).getFile();
      String decodedFilePath = URLDecoder.decode(filePath, "UTF-8");
      File file = new File(decodedFilePath);
      String data = FileUtils.readFileToString(file, "utf-8");
      String htmlMsg = String.format(data,
          preheader,
          welcome,
          user.getName(),
          message,
          confirmationUrl,
          registerButtonText,
          alternativeUrlPrefix + confirmationUrl,
          emailLastText,
          footerText
      );

      mimeMessage.setContent(htmlMsg, "text/html; charset=UTF-8");
      helper.setTo(recipientAddress);
      helper.setSubject(subject);
      helper.setFrom(env.getProperty("support.email"));

    } catch (MessagingException | IOException e) {
      e.printStackTrace();
    }

    return mimeMessage;
  }
}
