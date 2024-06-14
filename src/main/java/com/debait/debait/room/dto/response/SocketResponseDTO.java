package com.debait.debait.room.dto.response;

import com.debait.debait.room.entity.Room;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class SocketResponseDTO {
    @JsonProperty("room_id")
    private String id;

    public SocketResponseDTO(String roomId){
        this.id = roomId;
    }
}
