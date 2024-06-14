package com.debait.debait.room.service;

import com.debait.debait.room.entity.RoomSocket;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class SocketClient {

    @Value("${socket.server.url}")
    private String socketServerUrl;

    private final RestTemplate restTemplate;

    public SocketClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void sendRoomInfo(RoomSocket.RoomInfo roomInfo) {
        try {
            restTemplate.postForObject(socketServerUrl, roomInfo, String.class);
            System.out.println("RoomInfo sent successfully to socket server.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
