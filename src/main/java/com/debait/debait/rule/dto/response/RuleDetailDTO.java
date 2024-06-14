package com.debait.debait.rule.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class RuleDetailDTO {
    private String debater;
    private String msg;
    private int time;
}
