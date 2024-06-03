package com.debait.debait.rule.dto.response;

import com.debait.debait.rule.entity.Rule;
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
    private String spec;
    private String user_id;

    public RuleInfoResponseDTO(Rule rule) {
        this.id = rule.getId();
        this.rule_name = rule.getRuleName();
        this.spec = rule.getSpec();
        this.user_id = rule.getUser().getId();
    }

    // Rule 객체를 이용하여 RuleInfoResponseDTO 객체 생성
    public static RuleInfoResponseDTO fromRule(Rule rule) {
        return new RuleInfoResponseDTO(rule.getId(), rule.getRuleName(), rule.getSpec(), rule.getUser().getId());
    }
}
