package com.debait.debait.room.dto.response;

import com.debait.debait.room.entity.Room;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class RoomUpdateResponseDTO {
    private String name;
    private LocalDateTime created_at;
    private String user_id;
    private String video_src;
    private String thumbnail_src;
    private String script; // TEXT
    private String rule_id;

    public RoomUpdateResponseDTO(Room room){
        this.name = room.getName();
        this.created_at = room.getCreated_at();
        this.user_id = room.getRule().getUser().getId();
        this.video_src = room.getVideo_src();
        this.thumbnail_src = room.getThumbnail_src();
        this.script = room.getScript();
        this.rule_id = room.getRule().getId();
    }
}
