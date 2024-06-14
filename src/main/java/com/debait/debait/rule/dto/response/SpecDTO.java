package com.debait.debait.rule.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SpecDTO {

    private int team_size;
    private int order_size;
    private List<RuleDetailDTO> rules;
}
