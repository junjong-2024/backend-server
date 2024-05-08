package com.debait.debait.rule.entity;

import com.debait.debait.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "rule")
@Getter
@NoArgsConstructor
public class Rule {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "rule_name")
    private String rule_name;

    @Column(name = "spec")
    private String spec;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Builder
    public Rule(String id, String rule_name, String spec, User user)
    {
        this.id = id;
        this.rule_name = rule_name;
        this.spec = spec;
        this.user = user;
    }
}
