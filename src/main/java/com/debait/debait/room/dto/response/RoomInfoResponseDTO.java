package com.debait.debait.room.dto.response;

import com.debait.debait.room.entity.Room;
import com.debait.debait.rule.dto.response.RuleInfoResponseDTO;
import com.debait.debait.rule.entity.Rule;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class RoomInfoResponseDTO {
    private String id;
    private String name;
    private LocalDateTime created_at;
    private String user_id;
    private String video_src;
    private String thumbnail_src;
    private String script; // TEXT
    private String rule_id;

    public RoomInfoResponseDTO(Room room) {
        this.id = room.getId();
        this.name = room.getName();
        this.created_at = room.getCreated_at();
        this.user_id = room.getRule().getUser().getId();
        this.video_src = room.getVideo_src();
        this.thumbnail_src = room.getThumbnail_src();
        this.script = room.getScript();
        this.rule_id = room.getRule().getId();
    }

    public static RoomInfoResponseDTO fromRoom(Room room) {
        RoomInfoResponseDTO dto = new RoomInfoResponseDTO();
        dto.setId(room.getId());
        dto.setName(room.getName());
        dto.setCreated_at(room.getCreated_at());
        dto.setUser_id(room.getUser().getId());
        dto.setVideo_src(room.getVideo_src());
        dto.setThumbnail_src(room.getThumbnail_src());
        dto.setScript(room.getScript());
        dto.setRule_id(room.getRule().getId());
        return dto;
    }
}
