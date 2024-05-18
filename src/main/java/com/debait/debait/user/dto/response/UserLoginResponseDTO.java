package com.debait.debait.user.dto.response;

import com.debait.debait.user.entity.User;
import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginResponseDTO {
    private String login_id;
    private String token;
    private String errorMessage;
    //private HttpStatus status;

    public UserLoginResponseDTO(User user) {
        this.login_id = user.getLogin_id();
    }

    public UserLoginResponseDTO (String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
