package com.ibm.rms.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 消息队列配置类
 *
 * @author 刘嘉宁
 * @date 2022-12-20 20:46:33
 */
@Configuration
public class RabbitConfig {

    @Bean("directExchange")
    public Exchange exchange() {
        return ExchangeBuilder
                .directExchange("amp.direct")
                .build();
    }

    @Bean("mailQueue")
    public Queue queue() {
        return QueueBuilder
                .durable("mail")
                .build();
    }

    @Bean("binding")
    public Binding binding(@Qualifier("directExchange") Exchange exchange,
                           @Qualifier("mailQueue") Queue queue) {
        return BindingBuilder
                .bind(queue)
                .to(exchange)
                .with("my-mail")
                .noargs();
    }

    @Bean
    public Jackson2JsonMessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }

}
