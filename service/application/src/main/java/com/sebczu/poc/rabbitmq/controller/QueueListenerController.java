package com.sebczu.poc.rabbitmq.controller;

import com.sebczu.poc.rabbitmq.QueueConfiguration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/queue/listener")
@RequiredArgsConstructor
public class QueueListenerController {

  private final RabbitTemplate rabbitTemplate;
  private final Queue queueListener;

  @PostMapping
  public void sendToQueue(@RequestParam(value = "message") String message) {
    rabbitTemplate.convertAndSend(queueListener.getName(), message);
    log.info("message: {} send into queue: {}", message, queueListener.getName());
  }

  @RabbitListener(queues = QueueConfiguration.QUEUE_LISTENER_NAME)
  public void getFromQueue(Message message, @Header(AmqpHeaders.CONSUMER_TAG) String consumerTag){
    log.info("message: {} receive from queue: {}", new String(message.getBody()), queueListener.getName());
    log.info("message properties:");
    log.info("appId: {}", message.getMessageProperties().getAppId());
    log.info("correlationId: {}", message.getMessageProperties().getCorrelationId());
    log.info("deliveryTag: {}", message.getMessageProperties().getDeliveryTag());
    log.info("deliveryMode: {}", message.getMessageProperties().getDeliveryMode());
    log.info("consumerTag: {}", consumerTag);
  }

}
