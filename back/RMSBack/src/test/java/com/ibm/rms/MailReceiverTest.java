package com.ibm.rms;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MailReceiverTest {

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Test
    public void process() {
        //向消息队列发送消息
        amqpTemplate.convertAndSend("mail","test");
    }

    @Test
    public void testProcess() {
        // 向exchange的某个key发送消息，process3接收到消息
        amqpTemplate.convertAndSend("amq.direct", "my-mail", "test");
    }

}
