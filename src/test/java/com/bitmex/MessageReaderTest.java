package com.bitmex;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.Test;

class MessageReaderTest {

  MessageReader messageReader = new MessageReader();

  @Test
  public void readUpdateMessage() throws Exception {

    List<Order> orders = messageReader.readOrders(
        "{\"table\":\"orderBookL2_25\",\"action\":\"update\",\"data\":[{\"symbol\":\"XBTUSD\",\"id\":15599300950,\"side\":\"Buy\",\"size\":8446}]}");

    Order order = orders.get(0);
    assertEquals(15599300950L, order.getId());
    assertEquals("XBTUSD", order.getSymbol());
    assertEquals("Buy", order.getSide());
    assertEquals(8446, order.getSize());
  }
}