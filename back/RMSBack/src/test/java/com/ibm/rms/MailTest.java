package com.ibm.rms;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@SpringBootTest
@Component
public class MailTest {

    @Autowired
    private JavaMailSender javaMailSender;

    @Test
    public void testSendMail() {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("songyue@mail.rms.cool");
        // 邮件接收人
        mailMessage.setTo("gengchen@mail.rms.cool");
        // 邮件主题
        mailMessage.setSubject("SpringBoot Auto Send");
        // 邮件内容
        mailMessage.setText("test");
        javaMailSender.send(mailMessage);
    }

}
