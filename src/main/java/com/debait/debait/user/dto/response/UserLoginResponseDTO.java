package com.debait.debait.user.dto.response;

import com.debait.debait.user.entity.User;
import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginResponseDTO {
    private String login_id;


    public UserLoginResponseDTO(User user) {
        this.login_id = user.getLogin_id();
    }
}
