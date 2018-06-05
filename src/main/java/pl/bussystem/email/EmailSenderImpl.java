package pl.bussystem.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class EmailSenderImpl implements EmailSender {
  private JavaMailSender javaMailSender;

  @Autowired
  public EmailSenderImpl(JavaMailSender javaMailSender) {
    this.javaMailSender = javaMailSender;
  }


  @Override
  public void sendEmail(String to, String title, String content,
                        String attachmentName, InputStreamSource iss, String attachmentContentTye) {
    MimeMessage mail = javaMailSender.createMimeMessage();
    try {
      MimeMessageHelper helper = new MimeMessageHelper(mail, true);
      helper.setTo(to);
      helper.setReplyTo("noreply@januszpol.pl");
      helper.setFrom("bus.system.mail@gmail.com");
      helper.setSubject(title);
      helper.setText(content, true);
      if (attachmentName != null) {
        helper.addAttachment(attachmentName, iss, attachmentContentTye);
      }

    } catch (MessagingException e) {
      e.printStackTrace();
    }

    javaMailSender.send(mail);
  }
}
