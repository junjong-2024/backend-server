package com.debait.debait.room.dto.request;

import com.debait.debait.room.entity.Room;
import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class RoomUpdateRequestDTO {
    private String video_src;
    private String thumbnail_src;
    private String script;

    public RoomUpdateRequestDTO(Room room) {
        this.video_src = room.getVideo_src();
        this.thumbnail_src = room.getThumbnail_src();
        this.script = room.getScript();
    }

}
