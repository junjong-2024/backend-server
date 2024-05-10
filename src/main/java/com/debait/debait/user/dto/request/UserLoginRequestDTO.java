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
}
