package com.bitmex;

import java.net.URI;
import javax.websocket.ContainerProvider;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

public class SocketClientApplication {


  public static void main(String[] args) {

    WebSocketContainer container;
    Session session = null;

    try {
      container = ContainerProvider.getWebSocketContainer();
      session = container.connectToServer(WSClient.class, URI.create("wss://testnet.bitmex.com/realtime?subscribe=orderBookL2_25:XBTUSD"));
      WSClient.wait4TerminateSignal();

    } catch (Exception e) {

      e.printStackTrace();

    } finally {

      if (session != null) {
        try {
          session.close();
        } catch (Exception e) {

          e.printStackTrace();

        }
      }
    }
  }


}



