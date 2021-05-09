package com.example.demo;

import com.google.gson.Gson;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class SocketHandler extends TextWebSocketHandler {

    //Thread safe list
    List <WebSocketSession>sessions = new CopyOnWriteArrayList<>();

    Device device = new Device();

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message)
            throws InterruptedException, IOException {

        Map value = new Gson().fromJson(message.getPayload(), Map.class);
        //sätter status på devicet
        device.setState((String)value.get("device"));

        for(WebSocketSession webSocketSession : sessions) {
            webSocketSession.sendMessage(new TextMessage((String)value.get("device")));
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        //the messages will be broadcasted to all users.
        sessions.add(session);
        //läser av vårt device och skickar ut status
        session.sendMessage(new TextMessage(device.getStateString()));
    }
}