package com.sebczu.poc.rabbitmq;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExchangeTopicConfiguration {

  public static final String QUEUE_TOPIC_USERS_NAME = "queue-topic-users";
  public static final String QUEUE_TOPIC_USER_100_NAME = "queue-topic-user-100";
  public static final String QUEUE_TOPIC_USER_200_NAME = "queue-topic-user-200";

  public static final String EXCHANGE_TOPIC_USERS_ROUTE = "user.*";
  public static final String EXCHANGE_TOPIC_USER_100_ROUTE = "user.100";
  public static final String EXCHANGE_TOPIC_USER_200_ROUTE = "user.200";

  private static final String EXCHANGE_TOPIC_NAME = "exchange-topic";

  @Bean
  public Queue queueTopicUsers() {
    return new Queue(QUEUE_TOPIC_USERS_NAME, false);
  }

  @Bean
  public Queue queueTopicUser100() {
    return new Queue(QUEUE_TOPIC_USER_100_NAME, false);
  }

  @Bean
  public Queue queueTopicUser200() {
    return new Queue(QUEUE_TOPIC_USER_200_NAME, false);
  }

  @Bean
  public TopicExchange exchangeTopic() {
    return new TopicExchange(EXCHANGE_TOPIC_NAME);
  }

  @Bean
  public Declarables topicBinding() {
    return new Declarables(
        BindingBuilder.bind(queueTopicUsers()).to(exchangeTopic()).with(EXCHANGE_TOPIC_USERS_ROUTE),
        BindingBuilder.bind(queueTopicUser100()).to(exchangeTopic()).with(EXCHANGE_TOPIC_USER_100_ROUTE),
        BindingBuilder.bind(queueTopicUser200()).to(exchangeTopic()).with(EXCHANGE_TOPIC_USER_200_ROUTE)
    );
  }

}
