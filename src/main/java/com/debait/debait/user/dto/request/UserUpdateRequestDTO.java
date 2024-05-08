package com.debait.debait.user.dto.request;

import com.debait.debait.user.entity.User;
import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateRequestDTO {
    private String name;
    private String user_email;
    private String password;

    public UserUpdateRequestDTO(User user) {
        this.name = user.getName();
        this.user_email = user.getUser_email();
        this.password = user.getPassword();
    }
}
