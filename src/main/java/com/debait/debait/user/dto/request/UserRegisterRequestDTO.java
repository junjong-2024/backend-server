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

    // DTO를 Entity로 변환하는 메소드
    // DTO를 User 엔티티로 변환하여 서비스 계층에서 사용가능
    public User toEntity() {
        return User.builder()
                .name(this.name)
                .login_id(this.login_id)
                .user_email(this.user_email)
                .password(this.password)
                .build();
    }

}
