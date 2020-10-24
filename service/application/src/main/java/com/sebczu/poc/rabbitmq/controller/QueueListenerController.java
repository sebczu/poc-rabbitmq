package com.sebczu.poc.rabbitmq.controller;

import com.sebczu.poc.rabbitmq.RabbitmqConfiguration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
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

  @RabbitListener(queues = RabbitmqConfiguration.QUEUE_LISTENER_NAME)
  private void getFromQueue(String message){
    log.info("message: {} receive from queue: {}", message, queueListener.getName());
  }

}
