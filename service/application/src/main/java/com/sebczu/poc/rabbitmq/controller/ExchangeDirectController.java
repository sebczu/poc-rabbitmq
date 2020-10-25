package com.sebczu.poc.rabbitmq.controller;

import com.sebczu.poc.rabbitmq.ExchangeDirectConfiguration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/exchange/direct")
@RequiredArgsConstructor
public class ExchangeDirectController {

  private final RabbitTemplate rabbitTemplate;
  private final DirectExchange exchangeDirect;
  private final Queue queueDirect1;
  private final Queue queueDirect2;

  @PostMapping("/route1")
  public void sendToExchangeDirectRoute1(@RequestParam(value = "message") String message) {
    rabbitTemplate.convertAndSend(exchangeDirect.getName(), ExchangeDirectConfiguration.EXCHANGE_DIRECT_ROUTE_1, message);
    log.info("message: {} send into exchange: {} with route: {}", message, exchangeDirect.getName(), ExchangeDirectConfiguration.EXCHANGE_DIRECT_ROUTE_1);
  }

  @PostMapping("/route2")
  public void sendToExchangeDirectRoute2(@RequestParam(value = "message") String message) {
    rabbitTemplate.convertAndSend(exchangeDirect.getName(), ExchangeDirectConfiguration.EXCHANGE_DIRECT_ROUTE_2, message);
    log.info("message: {} send into exchange: {} with route: {}", message, exchangeDirect.getName(), ExchangeDirectConfiguration.EXCHANGE_DIRECT_ROUTE_2);
  }

  @PostMapping("/routeAll")
  public void sendToExchangeDirectRouteAll(@RequestParam(value = "message") String message) {
    rabbitTemplate.convertAndSend(exchangeDirect.getName(), ExchangeDirectConfiguration.EXCHANGE_DIRECT_ROUTE_ALL, message);
    log.info("message: {} send into exchange: {} with route: {}", message, exchangeDirect.getName(), ExchangeDirectConfiguration.EXCHANGE_DIRECT_ROUTE_ALL);
  }

  @RabbitListener(queues = ExchangeDirectConfiguration.QUEUE_DIRECT_1_NAME)
  public void getFromQueueDirect1(Message message){
    log.info("message: {} receive from queue: {}", new String(message.getBody()), queueDirect1.getName());
  }

  @RabbitListener(queues = ExchangeDirectConfiguration.QUEUE_DIRECT_2_NAME)
  public void getFromQueueDirect2(Message message){
    log.info("message: {} receive from queue: {}", new String(message.getBody()), queueDirect2.getName());
  }
}
