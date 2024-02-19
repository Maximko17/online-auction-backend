package com.example.onlineauction.service;

import com.example.onlineauction.entity.UserEntity;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.mail.javamail.JavaMailSender;
import freemarker.template.Configuration;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MailService {

    private final Configuration configuration;
    private final JavaMailSender mailSender;

    @SneakyThrows
    public void sendRegistrationEmail(final String email, String token) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,
                false,
                "UTF-8");
        helper.setSubject("Thank you for registration");
        helper.setFrom("maxim.suhodolets@yandex.ru");
        helper.setTo(email);
        String emailContent = getRegistrationEmailContent(token);
        helper.setText(emailContent, true);
        mailSender.send(mimeMessage);
    }

    @SneakyThrows
    private String getRegistrationEmailContent(final String token) {
        StringWriter writer = new StringWriter();
        Map<String, Object> model = new HashMap<>();
        model.put("token", token);
        configuration.getTemplate("register.ftlh")
                .process(model, writer);
        return writer.getBuffer().toString();
    }

}
