package com.debait.debait.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "discussion_room")
@Getter
@NoArgsConstructor
public class Room {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "created_at")
    private LocalDateTime created_at;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(name = "video_src")
    private String video_src;

    @Column(name = "thumbnail_src")
    private String thumbnail_src;

    @Column(name = "script")
    private String script;

    @ManyToOne
    @JoinColumn(name = "rule_id", referencedColumnName = "id")
    private Rule rule;

    @Builder
    public Room(String id, String name, LocalDateTime created_at, User user, String video_src, String thumbnail_src, String script, Rule rule) {
        this.id = id;
        this.name = name;
        this.created_at = created_at;
        this.user = user;
        this.video_src = video_src;
        this.thumbnail_src = thumbnail_src;
        this.script = script;
        this.rule = rule;
    }

}
