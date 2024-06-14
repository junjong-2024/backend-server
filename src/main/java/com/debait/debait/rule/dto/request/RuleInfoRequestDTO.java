package com.debait.debait.rule.dto.request;

import com.debait.debait.rule.entity.Rule;
import com.debait.debait.user.entity.User;
import com.debait.debait.user.repository.UserRepository;
import com.google.gson.Gson;
import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor

public class RuleInfoRequestDTO {
   @NotNull
    private String rule_name;
    private String user_id;
    private SpecDTO spec;

    private UserRepository userRepository;

    public RuleInfoRequestDTO(String ruleName, String user_id, UserRepository userRepository) {
        this.rule_name = ruleName;
        this.user_id = user_id;
        //this.spec = spec;
        this.userRepository = userRepository;
    }

    public Rule toEntity(User user) {
        return Rule.builder()
                .ruleName(this.rule_name)
                .spec(new Gson().toJson(this.spec))
                .user(user)
                .build();
    }
}
