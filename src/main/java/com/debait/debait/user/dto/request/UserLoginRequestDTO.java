package com.debait.debait.user.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginRequestDTO {

    @NotNull // 해당 필드는 Null이 될 수 없음을 명시
    @Size(min = 3, max = 50) // 문자열 길이 최소 3, 최대 50으로 제한
    private String login_id;

    @NotNull
    //@Size(min = 3, max = 100)
    // 클라이언트로부터 비밀번호를 받을 때 유용, 서버가 응답을 보낼 때는 비밀번호를 포함시키지 않는다.
    //@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
}
