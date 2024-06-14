package com.debait.debait.rule.dto.response;

import com.debait.debait.rule.entity.Rule;
import com.google.gson.Gson;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RuleInfoResponseDTO {
    private String id;
    private String rule_name;
    private SpecDTO spec;
    private String user_id;

    public RuleInfoResponseDTO(Rule rule) {
        this.id = rule.getId();
        this.rule_name = rule.getRuleName();

        // JSON 문자열을 SpecDTO 객체로 변환
        Gson gson = new Gson();
        this.spec = gson.fromJson(rule.getSpec(), SpecDTO.class);
        this.user_id = rule.getUser().getId();
    }

    // Rule 객체를 이용하여 RuleInfoResponseDTO 객체 생성
    public static RuleInfoResponseDTO fromRule(Rule rule) {
        Gson gson = new Gson();
        SpecDTO spec = gson.fromJson(rule.getSpec(), SpecDTO.class);

        return RuleInfoResponseDTO.builder()
                .id(rule.getId())
                .rule_name(rule.getRuleName())
                .spec(spec)
                .user_id(rule.getUser().getId())
                .build();
    }
}