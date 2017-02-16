package com.jy.activiti.websocket;

import javax.websocket.MessageHandler;
import javax.websocket.Session;
import java.io.IOException;
import java.util.Map;

public class EchoStringMessageHandler implements MessageHandler.Whole<String>{

    private String username;
    @Override
    public void onMessage(String message) {
        System.out.println("User[" + username +"]: " + message);
        for (Map.Entry<String, Session> entry: SessionUtils.clients.entrySet()) {
            try {
                entry.getValue().getBasicRemote().sendText(username + " : " +message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public EchoStringMessageHandler(String username) {
        this.username = username;
    }
}
