package com.sebczu.poc.rabbitmq.controller;

import com.rabbitmq.client.Channel;
import com.sebczu.poc.rabbitmq.PubSubConfiguration;
import com.sebczu.poc.rabbitmq.QueueConfiguration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/pubsub/fanout")
@RequiredArgsConstructor
public class PubSubSimpleController {

  private final RabbitTemplate rabbitTemplate;
  private final FanoutExchange exchangeFanout;
  private final Queue queueFanout1;
  private final Queue queueFanout2;

  @PostMapping
  public void sendToQueue(@RequestParam(value = "message") String message) {
    rabbitTemplate.convertAndSend(exchangeFanout.getName(), "", message);
    log.info("message: {} send into exchange: {}", message, exchangeFanout.getName());
  }

  @RabbitListener(queues = PubSubConfiguration.QUEUE_FANOUT_1_NAME)
  public void getFromQueueFanout1(Message message){
    log.info("message: {} receive from queue: {}", new String(message.getBody()), queueFanout1.getName());
  }

  @RabbitListener(queues = PubSubConfiguration.QUEUE_FANOUT_2_NAME)
  public void getFromQueueFanout2(Message message){
    log.info("message: {} receive from queue: {}", new String(message.getBody()), queueFanout2.getName());
  }
}
