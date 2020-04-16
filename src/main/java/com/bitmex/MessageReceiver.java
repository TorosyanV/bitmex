package com.bitmex;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class MessageReceiver {

  private static final Logger logger = Logger.getLogger(MessageReceiver.class.getName());
  private final MessageReader messageReader = new MessageReader();
  private final Map<Long, Order> ordersMap = new HashMap<>();


  public void onMessage(String message) throws JsonProcessingException {
    String action = "unknown";
    try {
      action = messageReader.readAction(message);
      logger.info("action: " + action);
    } catch (JsonProcessingException e) {
      logger.warning(e.getMessage());
    }

    switch (action) {
      case "update":
        onUpdate(message);
        break;
      case "insert":
        onInsert(message);
        break;
      case "delete":
        onDelete(message);
        break;
      case "unknown":
        logger.warning("unknown action detected");

    }
  }

  private void onInsert(String message) throws JsonProcessingException {
    List<Order> orders = messageReader.readOrders(message);
    for (Order order : orders) {
      ordersMap.put(order.getId(), order);
//      postOrder(order);
    }
  }

  private void onDelete(String message) throws JsonProcessingException {
    List<Order> orders = messageReader.readOrders(message);

    orders.forEach(order -> {
      ordersMap.remove(order.getId());
    });
  }

  private void onUpdate(String message) throws JsonProcessingException {
    List<Order> orders = messageReader.readOrders(message);
    for (Order order : orders) {
      if (ordersMap.containsKey(order.getId())) {
        Order oldOrder = ordersMap.get(order.getId());
        order.setPrice(oldOrder.getPrice());
      }
      ordersMap.put(order.getId(), order);
    }
  }


  public Collection<Order> getOrders() {
    return ordersMap.values();
  }

  public void postOrder(Order order) {
// need to authorize
    RestTemplate restTemplate = new RestTemplate();
    ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity("https://testnet.bitmex.com/api/v1/order", order, String.class);
    logger.info(stringResponseEntity.getBody());
  }
}
