package com.jy.activiti.websocket;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;

/**
 * 功能说明：websocket处理类, 使用J2EE7的标准
 * 切忌直接在该连接处理类中加入业务处理代码
 * 作者：liuxing(2014-11-14 04:20)
 */
//relationId和userCode是我的业务标识参数,websocket.ws是连接的路径，可以自行定义
@ServerEndpoint("/anno/websocket/{relationId}/{username}")
public class AnnoWebsocketEndPoint {

    private static Log log = LogFactory.getLog(AnnoWebsocketEndPoint.class);

    @OnOpen
    public void onOpen(@PathParam("relationId") String relationId, @PathParam("username") String username, Session session) {
        System.out.println("Client connected: " + username);
        SessionUtils.put(relationId, username, session);
    }

    @OnMessage
    public void onMessage(@PathParam("relationId") String relationId, @PathParam("username") String username, String message, Session session)
            throws IOException, InterruptedException {
        // Print the client message for testing purposes
        System.out.println("User[" + username + "]: " + message);
        for (Map.Entry<String, Session> entry : SessionUtils.clients.entrySet()) {
            entry.getValue().getBasicRemote().sendText(username + " : " + message);
        }
    }

    @OnError
    public void onError(Throwable throwable, Session session) {
        throwable.printStackTrace();
    }

    @OnClose
    public void onClose(Session session) {
        System.out.println("Connection closed");
    }

}