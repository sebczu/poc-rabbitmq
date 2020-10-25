package com.sebczu.poc.rabbitmq.controller;

import com.sebczu.poc.rabbitmq.ExchangeTopicConfiguration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/exchange/topic")
@RequiredArgsConstructor
public class ExchangeTopicController {

  private final RabbitTemplate rabbitTemplate;
  private final TopicExchange topicExchange;
  private final Queue queueTopicUsers;
  private final Queue queueTopicUser100;
  private final Queue queueTopicUser200;

  @PostMapping("/user100")
  public void sendToExchangeTopicUser100(@RequestParam(value = "message") String message) {
    rabbitTemplate.convertAndSend(topicExchange.getName(), "user.100", message);
    log.info("message: {} send into exchange: {} with route: {}", message, topicExchange.getName(), "user.100");
  }

  @PostMapping("/user200")
  public void sendToExchangeTopicUser200(@RequestParam(value = "message") String message) {
    rabbitTemplate.convertAndSend(topicExchange.getName(), "user.200", message);
    log.info("message: {} send into exchange: {} with route: {}", message, topicExchange.getName(), "user.200");
  }

  @RabbitListener(queues = ExchangeTopicConfiguration.QUEUE_TOPIC_USERS_NAME)
  public void getFromQueueTopicUsers(Message message) {
    log.info("message: {} receive from queue: {}", new String(message.getBody()), queueTopicUsers.getName());
  }

  @RabbitListener(queues = ExchangeTopicConfiguration.QUEUE_TOPIC_USER_100_NAME)
  public void getFromQueueTopicUser100(Message message) {
    log.info("message: {} receive from queue: {}", new String(message.getBody()), queueTopicUser100.getName());
  }

  @RabbitListener(queues = ExchangeTopicConfiguration.QUEUE_TOPIC_USER_200_NAME)
  public void getFromQueueTopicUser200(Message message) {
    log.info("message: {} receive from queue: {}", new String(message.getBody()), queueTopicUser200.getName());
  }
}
