package com.ibm.rms.mq;

import com.ibm.rms.domain.Mail;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

/**
 * 邮件 Rabbit MQ 消息队列 接收端
 *
 * @author 刘嘉宁
 * @date 2022-12-20 21:22:25
 */
@Component
public class MailListener {

    @Autowired
    private JavaMailSender javaMailSender;

    // @RabbitListener(queues = "mail")
    // @RabbitListener(bindings = {@QueueBinding(value = @Queue(name = "mail"), exchange = @Exchange(name = "rms"), key = "my-mail")})
    // public void process(Mail mail) {
    //     SimpleMailMessage mailMessage = new SimpleMailMessage();
    //     // 邮件发送人
    //     mailMessage.setFrom(mail.getFrom());
    //     // 邮件接收人
    //     mailMessage.setTo(mail.getTo());
    //     // 邮件主题
    //     mailMessage.setSubject(mail.getSubject());
    //     // 邮件内容
    //     mailMessage.setText(mail.getText());
    //     // 发送邮件
    //     javaMailSender.send(mailMessage);
    // }

}
