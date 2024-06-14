package com.debait.debait.room.service;

import com.debait.debait.room.dto.response.SocketResponseDTO;
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

    public SocketResponseDTO sendRoomInfo(RoomSocket.RoomInfo roomInfo, String roomId) {
        SocketResponseDTO a = restTemplate.postForObject(socketServerUrl+"/room/"+roomId, roomInfo, SocketResponseDTO.class);
        System.out.println(a.getId());
        System.out.println("RoomInfo sent successfully to socket server.");
        return a;
    }


}
