package com.example.inapp_message_demo.websocket;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Component
public class WebSocketServer extends TextWebSocketHandler {

    private static final ConcurrentMap<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String receiverId = session.getUri().getQuery().split("=")[1];
        sessions.put(receiverId, session);
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // Handle incoming messages from client if needed
        System.out.println("Received message: " + message.getPayload());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.values().remove(session);
    }

    public void sendMessage(String receiverId, String content) throws Exception {

        WebSocketSession session = sessions.get(receiverId);
        if (session != null && session.isOpen()) {
            session.sendMessage(new TextMessage(content));
        }
    }
}
