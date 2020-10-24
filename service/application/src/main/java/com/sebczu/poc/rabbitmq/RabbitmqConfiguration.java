package com.sebczu.poc.rabbitmq;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.amqp.core.Queue;

@Configuration
public class RabbitmqConfiguration {

  public static final String QUEUE_SIMPLE_NAME = "queue-simple";
  public static final String QUEUE_LISTENER_NAME = "queue-listener";

  @Bean
  public Queue queueSimple() {
    return new Queue(QUEUE_SIMPLE_NAME, false);
  }

  @Bean
  public Queue queueListener() {
    return new Queue(QUEUE_LISTENER_NAME, false);
  }

}
