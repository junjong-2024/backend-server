package com.debait.debait.rule.entity;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RuleDetail {
    private String debater;
    private String msg;
    private int time;
}
