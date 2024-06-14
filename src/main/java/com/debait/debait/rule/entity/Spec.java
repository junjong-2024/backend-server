package com.debait.debait.rule.entity;

import com.google.gson.annotations.SerializedName;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Spec {
    @SerializedName("team_size")
    private int teamSize;

    @SerializedName("order_size")
    private int orderSize;

    private List<RuleDetail> rules;
}
