package com.bitmex;

import com.fasterxml.jackson.core.JsonProcessingException;
import javax.websocket.ClientEndpoint;
import javax.websocket.OnMessage;

@ClientEndpoint

public class WSClient {

  private static Object waitLock = new Object();


  private MessageReceiver messageReceiver = new MessageReceiver();

  @OnMessage
  public void onMessage(String message) throws JsonProcessingException {
    System.out.println("Received msg: " + message);
    try {
      if (message.startsWith("{\"table\":\"orderBookL2_25\",\"action\"")) {
        messageReceiver.onMessage(message);
      }

    } catch (JsonProcessingException e) {

    }

  }

  public static void wait4TerminateSignal() {

    synchronized (waitLock) {
      try {

        waitLock.wait();

      } catch (InterruptedException e) {

      }
    }
  }


}