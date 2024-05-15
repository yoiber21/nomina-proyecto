package org.example.nominatercercorte.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.io.File;
import java.util.Objects;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailParseException;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.JavaMailSender;

@Service
public class EmailService {

  private final JavaMailSender mailSender;

  public EmailService(JavaMailSender mailSender) {
    this.mailSender = mailSender;
  }

  public void sendPayrollNotification(String to, String subject, String body, String pdfPath) {
    MimeMessage message = mailSender.createMimeMessage();

    try {
      MimeMessageHelper helper = new MimeMessageHelper(message, true);
      helper.setTo(to);
      helper.setSubject(subject);
      helper.setText(body);

      FileSystemResource file = new FileSystemResource(new File(pdfPath));
      helper.addAttachment(Objects.requireNonNull(file.getFilename()), file);
    } catch (MessagingException e) {
      throw new MailParseException(e);
    }

    mailSender.send(message);
  }
}