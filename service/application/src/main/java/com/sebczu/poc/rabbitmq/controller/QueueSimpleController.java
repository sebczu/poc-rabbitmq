package com.sebczu.poc.rabbitmq.controller;

import com.sebczu.poc.rabbitmq.RabbitmqConfiguration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/queue/simple")
@RequiredArgsConstructor
public class QueueSimpleController {

  private final RabbitTemplate rabbitTemplate;

  @PostMapping
  public void sendToQueue(@RequestParam(value = "message") String message) {
    rabbitTemplate.convertAndSend(RabbitmqConfiguration.QUEUE_SIMPLE_NAME, message);
    log.info("message: {} send into queue: {}", message, RabbitmqConfiguration.QUEUE_SIMPLE_NAME);
  }

  @GetMapping
  public String getFromQueue() {
    Object message = rabbitTemplate.receiveAndConvert(RabbitmqConfiguration.QUEUE_SIMPLE_NAME);
    log.info("message: {} receive from queue: {}", message, RabbitmqConfiguration.QUEUE_SIMPLE_NAME);
    return message == null ? "any message exist" : message.toString();
  }

}
