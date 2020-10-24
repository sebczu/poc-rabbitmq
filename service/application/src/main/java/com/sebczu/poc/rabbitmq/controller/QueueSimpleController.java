package com.sebczu.poc.rabbitmq.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/queue/simple")
@RequiredArgsConstructor
public class QueueSimpleController {

  private final RabbitTemplate rabbitTemplate;
  private final Queue queueSimple;
  private final AmqpAdmin admin;

  @PostMapping
  public void sendToQueue(@RequestParam(value = "message") String message) {
    rabbitTemplate.convertAndSend(queueSimple.getName(), message);
    log.info("message: {} send into queue: {}", message, queueSimple.getName());
  }

  @GetMapping
  public String getFromQueue() {
    Object message = rabbitTemplate.receiveAndConvert(queueSimple.getName());
    log.info("message: {} receive from queue: {}", message, queueSimple.getName());
    return message == null ? "any message exist" : message.toString();
  }

  @DeleteMapping
  public void deleteQueue() {
    boolean isSuccessful = admin.deleteQueue(queueSimple.getName());
    log.info("delete queue is successful: {}",isSuccessful);
  }

}
