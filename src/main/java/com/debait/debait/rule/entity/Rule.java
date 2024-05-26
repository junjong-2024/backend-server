package com.debait.debait.rule.entity;

import com.debait.debait.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "rule")
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Rule {
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

    @Column(name = "rule_name")
    private String ruleName;

    @Column(name = "spec")
    private String spec;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Builder
    public Rule(String id, String ruleName, String spec, User user)
    {
        this.id = id;
        this.ruleName = ruleName;
        this.spec = spec;
        this.user = user;
    }
}
