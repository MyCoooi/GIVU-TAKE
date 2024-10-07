package com.accepted.givutake.user.common.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeUtility;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.utils.IoUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender mailSender;

    public void sendEmail(String email, String subject, String text) throws MessagingException {

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true); // true indicates multipart message

        helper.setTo(email);
        helper.setSubject(subject);
        helper.setText(text, true); // true indicates HTML

        mailSender.send(message);
    }

    public void sendMultipleMessage(String email, String subject, String text) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setSubject(subject);
        helper.setText(text, true);
        helper.setTo(email);

        //첨부 파일 설정
        String fileName = "ex_springboot.pdf";
        File pdfFile = new File(fileName);
        try (FileInputStream fis = new FileInputStream(pdfFile)) {
            helper.addAttachment(
                    MimeUtility.encodeText(fileName, "UTF-8", "B"),
                    new ByteArrayResource(IoUtils.toByteArray(fis))
            );
        } catch (IOException e) {
            log.error(e.getMessage());
        }

        // 전송
        mailSender.send(message);
    }


}
