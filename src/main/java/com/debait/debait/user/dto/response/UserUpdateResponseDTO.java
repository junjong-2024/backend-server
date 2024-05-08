package com.debait.debait.user.dto.response;

import com.debait.debait.user.entity.User;
import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateResponseDTO {
    private String name;
    private String login_id;
    private String user_email;
    private String password;

    public UserUpdateResponseDTO(User user) {
        this.name = user.getName();
        this.login_id = user.getLogin_id();
        this.user_email = user.getUser_email();
        this.password = user.getPassword();
    }
}
