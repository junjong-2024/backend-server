package com.debait.debait.user.dto.request;

import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginRequestDTO {
    private String login_id;
    private String password;

    public String getLogin_id() {
        return login_id;
    }

    public String getPassword() {
        return password;
    }
}
