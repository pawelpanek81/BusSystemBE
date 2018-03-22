package pl.bussystem.security.email.verification.service;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Objects;

@Service
public class SendEmailService {
  private Environment env;
  private JavaMailSender mailSender;

  @Autowired
  public SendEmailService(Environment env, JavaMailSender mailSender) {
    this.env = env;
    this.mailSender = mailSender;
  }

  public void sendEmail(MimeMessage email) {
    mailSender.send(email);
  }

  // e.g. templateURI: static/mailtemplate.html
  public MimeMessage constructEmailMessage(String templateURI,
                                           List<? extends String> emailTemplateVariables,
                                           String recipientAddress,
                                           String subject) {
    MimeMessage mimeMessage = mailSender.createMimeMessage();
    MimeMessageHelper helper = null;

    try {
      helper = new MimeMessageHelper(mimeMessage, false, "UTF-8");

      ClassLoader classLoader = getClass().getClassLoader();
      String filePath = Objects.requireNonNull(classLoader.getResource(templateURI)).getFile();
      String decodedFilePath = URLDecoder.decode(filePath, "UTF-8");
      File file = new File(decodedFilePath);
      String data = FileUtils.readFileToString(file, "utf-8");
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