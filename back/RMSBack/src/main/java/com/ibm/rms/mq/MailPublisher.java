package com.ibm.rms.mq;

import com.ibm.rms.domain.Mail;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 邮件 Rabbit MQ 消息队列 发送端
 *
 * @author 刘嘉宁
 * @date 2022-12-20 21:19:04
 */
@Component
public class MailPublisher {

    @Autowired
    private AmqpTemplate amqpTemplate;

    public void process(Mail mail) {
        // 向 exchange 的某个 key 发送消息, process 接收到消息
        amqpTemplate.convertAndSend("amq.direct", "my-mail", mail);
    }

}
