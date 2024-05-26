package com.debait.debait.rule.dto.response;

import com.debait.debait.rule.entity.Rule;
import lombok.Getter;

@Getter
public class RuleInfoResponseDTO {
    private String id;
    private String ruleName;
    private String spec;
    private String user_id;

    public RuleInfoResponseDTO(Rule rule) {
        this.id = rule.getId();
        this.ruleName = rule.getRuleName();
        this.spec = rule.getSpec();
        this.user_id = rule.getUser().getId();
    }
}
