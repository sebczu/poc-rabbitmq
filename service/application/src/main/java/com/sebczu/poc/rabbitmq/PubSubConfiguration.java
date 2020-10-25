package com.sebczu.poc.rabbitmq;

import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Declarables;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PubSubConfiguration {

  public static final String QUEUE_FANOUT_1_NAME = "queue-fanout-1";
  public static final String QUEUE_FANOUT_2_NAME = "queue-fanout-2";

  private static final String EXCHANGE_FANOUT_NAME = "exchange-fanout";

  @Bean
  public Queue queueFanout1() {
    return new Queue(QUEUE_FANOUT_1_NAME, false);
  }

  @Bean
  public Queue queueFanout2() {
    return new Queue(QUEUE_FANOUT_2_NAME, false);
  }

  @Bean
  public FanoutExchange exchangeFanout() {
    return new FanoutExchange(EXCHANGE_FANOUT_NAME);
  }

  @Bean
  public Declarables fanoutBinding() {
    return new Declarables(
        BindingBuilder.bind(queueFanout1()).to(exchangeFanout()),
        BindingBuilder.bind(queueFanout2()).to(exchangeFanout())
    );
  }

}
