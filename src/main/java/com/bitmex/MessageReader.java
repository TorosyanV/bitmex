package com.bitmex;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MessageReader {

  private static final Logger logger = Logger.getLogger(MessageReader.class.getName());
  private final ObjectMapper objectMapper = new ObjectMapper();


  public List<Order> readOrders(String message) throws JsonProcessingException {

    JsonNode productNode = new ObjectMapper().readTree(message);

    List<Order> orders = null;
    try {
      orders = objectMapper.readValue(productNode.get("data").toString(), new TypeReference<List<Order>>() {
      });
    } catch (JsonProcessingException e) {
      logger.log(Level.WARNING, e.getMessage());
    }
    return orders;
  }

  public String readAction(String message) throws JsonProcessingException {
    JsonNode messageNode = new ObjectMapper().readTree(message);
    return messageNode.get("action").asText();

  }

}
