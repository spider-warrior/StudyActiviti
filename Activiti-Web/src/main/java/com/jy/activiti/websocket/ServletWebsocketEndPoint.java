package com.jy.activiti.websocket;

import javax.websocket.CloseReason;
import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.Session;
import java.util.Map;

public class ServletWebsocketEndPoint extends Endpoint {

    @Override
    public void onOpen(Session session, EndpointConfig config) {
        System.out.println("client connect");
        // Set maximum messages size to 10.000 bytes.
        Map<String,String> pathParameters = session.getPathParameters();
        String username = pathParameters.get("username");
        String relationId = pathParameters.get("relationId");
        SessionUtils.put(relationId, username, session);
        session.setMaxTextMessageBufferSize(10000);
        session.addMessageHandler(new EchoStringMessageHandler(username));
        System.out.println("Client connected: " + username);
    }

    @Override
    public void onError(Session session, Throwable throwable) {
        System.out.println("client error");
    }

    @Override
    public void onClose(Session session, CloseReason closeReason) {
        System.out.println("client cliose");
    }

}

