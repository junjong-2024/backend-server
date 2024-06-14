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
    private int teamSize;
    private int orderSize;
    private List<RuleDetailDTO> rules;
}
