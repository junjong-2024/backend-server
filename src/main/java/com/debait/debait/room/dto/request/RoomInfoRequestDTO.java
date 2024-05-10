package com.debait.debait.room.dto.request;

import com.debait.debait.room.entity.Room;
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
@NoArgsConstructor
public class RoomInfoRequestDTO {
    private String user_id;
    private String name;
    private LocalDateTime created_at;

    private UserRepository userRepository;

    public Room toEntity() {
        User user = userRepository.findById(this.user_id)
                .orElseThrow(() -> new IllegalArgumentException("일치한 회원이 없습니다."));

        return Room.builder()
                .name(this.name)
                .created_at(this.created_at)
                .user(user)
                .build();
    }
}
