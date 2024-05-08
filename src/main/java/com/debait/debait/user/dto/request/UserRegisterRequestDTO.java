package com.debait.debait.user.dto.request;

import com.debait.debait.user.entity.User;
import lombok.*;

@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterRequestDTO {
    private String name;
    private String login_id;
    private String user_email;
    private String password;

    public User toEntity() {
        return User.builder()
                .name(this.name)
                .login_id(this.login_id)
                .user_email(this.user_email)
                .password(this.password)
                .build();
    }

}
