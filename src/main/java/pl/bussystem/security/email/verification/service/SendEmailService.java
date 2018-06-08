package pl.bussystem.security.email.verification.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Objects;

@Service
public class SendEmailService {
  private Environment env;
  private JavaMailSender mailSender;
  private ResourceLoader resourceLoader;

  @Autowired
  public SendEmailService(Environment env, JavaMailSender mailSender, ResourceLoader resourceLoader) {
    this.env = env;
    this.mailSender = mailSender;
    this.resourceLoader = resourceLoader;
  }

  public void sendEmail(MimeMessage email) {
    mailSender.send(email);
  }

  // e.g. templateURI: static/registration-verification-mail-template.html
  public MimeMessage constructEmailMessage(String templateURI,
                                           List<? extends String> emailTemplateVariables,
                                           String recipientAddress,
                                           String subject) {
    MimeMessage mimeMessage = mailSender.createMimeMessage();
    MimeMessageHelper helper = null;

    try {
      helper = new MimeMessageHelper(mimeMessage, false, "UTF-8");

      ClassLoader classLoader = getClass().getClassLoader();

      String data = StreamUtils.copyToString(
          Objects.requireNonNull(resourceLoader.getResource("classpath:" + templateURI)).getInputStream(),
          Charset.forName("UTF-8")
      );

      Object[] varargs = emailTemplateVariables.toArray();
      String htmlMsg = String.format(data, varargs);

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