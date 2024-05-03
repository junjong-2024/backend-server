package com.debait.debait.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegisterRequestDTO {
    private String name;
    private String login_id;
    private String user_email;
    private String password;
}
