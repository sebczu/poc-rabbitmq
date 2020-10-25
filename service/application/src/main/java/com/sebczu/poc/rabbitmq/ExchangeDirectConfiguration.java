package com.sebczu.poc.rabbitmq;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExchangeDirectConfiguration {

  public static final String QUEUE_DIRECT_1_NAME = "queue-direct-1";
  public static final String QUEUE_DIRECT_2_NAME = "queue-direct-2";

  public static final String EXCHANGE_DIRECT_ROUTE_1 = "route1";
  public static final String EXCHANGE_DIRECT_ROUTE_2 = "route2";
  public static final String EXCHANGE_DIRECT_ROUTE_ALL = "routeAll";

  private static final String EXCHANGE_DIRECT_NAME = "exchange-direct";

  @Bean
  public Queue queueDirect1() {
    return new Queue(QUEUE_DIRECT_1_NAME, false);
  }

  @Bean
  public Queue queueDirect2() {
    return new Queue(QUEUE_DIRECT_2_NAME, false);
  }

  @Bean
  public DirectExchange exchangeDirect() {
    return new DirectExchange(EXCHANGE_DIRECT_NAME);
  }

  @Bean
  public Declarables directBinding() {
    return new Declarables(
        BindingBuilder.bind(queueDirect1()).to(exchangeDirect()).with(EXCHANGE_DIRECT_ROUTE_1),
        BindingBuilder.bind(queueDirect2()).to(exchangeDirect()).with(EXCHANGE_DIRECT_ROUTE_2),
        BindingBuilder.bind(queueDirect1()).to(exchangeDirect()).with(EXCHANGE_DIRECT_ROUTE_ALL),
        BindingBuilder.bind(queueDirect2()).to(exchangeDirect()).with(EXCHANGE_DIRECT_ROUTE_ALL)
    );
  }

}
