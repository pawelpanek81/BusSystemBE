package pl.bussystem.email;

import org.springframework.core.io.InputStreamSource;

public interface EmailSender {
  void sendEmail(String to, String subject, String content,
                 String attachmentName, InputStreamSource attach, String attachmentContentTye);
}