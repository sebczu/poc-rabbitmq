package com.sebczu.poc.rabbitmq.controller;

import com.rabbitmq.client.Channel;
import com.sebczu.poc.rabbitmq.RabbitmqConfiguration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/queue/ack")
@RequiredArgsConstructor
public class QueueListenerAckController {

  private final RabbitTemplate rabbitTemplate;
  private final Queue queueAck;

  @PostMapping
  public void sendToQueue(@RequestParam(value = "message") String message) {
    rabbitTemplate.convertAndSend(queueAck.getName(), message);
    log.info("message: {} send into queue: {}", message, queueAck.getName());
  }

  @RabbitListener(queues = RabbitmqConfiguration.QUEUE_ACK_NAME, ackMode = "MANUAL")
  public void getFromQueue1(Message message, Channel channel) throws IOException, InterruptedException {
    log.info("listener 1: nack");
    Thread.sleep(5000);
    channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
  }

  @RabbitListener(queues = RabbitmqConfiguration.QUEUE_ACK_NAME, ackMode = "MANUAL")
  public void getFromQueue2(Message message, Channel channel) throws IOException, InterruptedException {
    log.info("listener 2: nack");
    Thread.sleep(5000);
    channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
  }

  @RabbitListener(queues = RabbitmqConfiguration.QUEUE_ACK_NAME, ackMode = "MANUAL")
  public void getFromQueue3(Message message, Channel channel) throws IOException, InterruptedException {
    log.info("listener 3: ack");
    Thread.sleep(5000);
    channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
  }

}
