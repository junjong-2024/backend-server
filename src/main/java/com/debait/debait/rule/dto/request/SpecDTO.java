package com.debait.debait.rule.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SpecDTO {
    private int team_size;
    private int order_size;
    private List<RuleDetailDTO> rules;
}
