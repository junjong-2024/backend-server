package com.debait.debait.room.dto.request;

import com.debait.debait.room.entity.Room;
import com.debait.debait.rule.entity.Rule;
import com.debait.debait.user.entity.User;
import com.debait.debait.user.repository.UserRepository;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@RequiredArgsConstructor
public class RoomInfoRequestDTO {
    private String user_id;
    private String name;
    private LocalDateTime created_at;
    private String videoSrc;
    private String thumbnailSrc;
    private String script;

    private final UserRepository userRepository;

    public Room toEntity(User user, Rule rule) {

        return Room.builder()
                .name(this.name)
                .video_src(this.videoSrc)
                .thumbnail_src(this.thumbnailSrc)
                .script(this.script)
                .user(user)
                .rule(rule)
                .build();
    }
}
