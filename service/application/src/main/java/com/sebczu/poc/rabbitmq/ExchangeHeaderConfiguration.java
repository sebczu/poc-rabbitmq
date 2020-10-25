package com.sebczu.poc.rabbitmq;

import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Declarables;
import org.springframework.amqp.core.HeadersExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExchangeHeaderConfiguration {

  public static final String QUEUE_HEADER_USER_100_NAME = "queue-header-user-100";
  public static final String QUEUE_HEADER_USER_200_NAME = "queue-header-user-200";

  private static final String EXCHANGE_HEADER_NAME = "exchange-header";

  @Bean
  public Queue queueHeaderUser100() {
    return new Queue(QUEUE_HEADER_USER_100_NAME, false);
  }

  @Bean
  public Queue queueHeaderUser200() {
    return new Queue(QUEUE_HEADER_USER_200_NAME, false);
  }

  @Bean
  public HeadersExchange exchangeHeader() {
    return new HeadersExchange(EXCHANGE_HEADER_NAME);
  }

  @Bean
  public Declarables headerBinding() {
    return new Declarables(
        BindingBuilder.bind(queueHeaderUser100()).to(exchangeHeader()).where("user").matches("100"),
        BindingBuilder.bind(queueHeaderUser200()).to(exchangeHeader()).where("user").matches("200")
    );
  }

}
