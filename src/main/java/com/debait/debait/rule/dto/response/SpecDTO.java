package com.debait.debait.rule.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SpecDTO {

    private int teamSize;
    private int orderSize;
    private List<RuleDetailDTO> rules;
}
