package com.sebczu.poc.rabbitmq.controller;

import com.sebczu.poc.rabbitmq.ExchangeHeaderConfiguration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.HeadersExchange;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/exchange/header")
@RequiredArgsConstructor
public class ExchangeHeaderController {

  private final RabbitTemplate rabbitTemplate;
  private final HeadersExchange headerExchange;
  private final Queue queueHeaderUser100;
  private final Queue queueHeaderUser200;

  @PostMapping("/user100")
  public void sendToExchangeHeaderUser100(@RequestParam(value = "message") String text) {
    MessageProperties messageProperties = new MessageProperties();
    messageProperties.setHeader("user", "100");
    MessageConverter messageConverter = new SimpleMessageConverter();
    Message message = messageConverter.toMessage(text, messageProperties);

    rabbitTemplate.send(headerExchange.getName(), "", message);
    log.info("message: {} send into exchange: {} with header: {}", text, headerExchange.getName(), "user=100");
  }

  @PostMapping("/user200")
  public void sendToExchangeHeaderUser200(@RequestParam(value = "message") String text) {
    MessageProperties messageProperties = new MessageProperties();
    messageProperties.setHeader("user", "200");
    MessageConverter messageConverter = new SimpleMessageConverter();
    Message message = messageConverter.toMessage(text, messageProperties);

    rabbitTemplate.send(headerExchange.getName(), "", message);
    log.info("message: {} send into exchange: {} with header: {}", text, headerExchange.getName(), "user=200");
  }

  @RabbitListener(queues = ExchangeHeaderConfiguration.QUEUE_HEADER_USER_100_NAME)
  public void getFromQueueHeaderUser100(Message message) {
    log.info("message: {} receive from queue: {}", new String(message.getBody()), queueHeaderUser100.getName());
  }

  @RabbitListener(queues = ExchangeHeaderConfiguration.QUEUE_HEADER_USER_200_NAME)
  public void getFromQueueHeaderUser200(Message message) {
    log.info("message: {} receive from queue: {}", new String(message.getBody()), queueHeaderUser200.getName());
  }
}
