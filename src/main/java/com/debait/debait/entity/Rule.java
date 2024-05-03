package com.debait.debait.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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

    @Column(name = "user_id")
    private String user_id;

//    @ManyToOne
//    @JoinColumn(name = "user_id", referencedColumnName = "id")
//    private User user;
//
//    @OneToMany(mappedBy = "rule", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Room> rooms = new ArrayList<>();

    @Builder
    public Rule(String id, String rule_name, String spec, String user_id)
    {
        this.id = id;
        this.rule_name = rule_name;
        this.spec = spec;
        this.user_id = user_id;
    }
}
