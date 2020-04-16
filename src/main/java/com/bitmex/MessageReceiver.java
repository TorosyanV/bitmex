package com.bitmex;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

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
      case "remove":
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
}
