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

    @Column(name = "user_id")
    private String user_id;

    @Column(name = "video_src")
    private String video_src;

    @Column(name = "thumbnail_src")
    private String thumbnail_src;

    @Column(name = "script")
    private String script;

    @Column(name = "rule_id")
    private String rule_id;

//    @ManyToOne
//    @JoinColumn(name = "user_id", referencedColumnName = "id")
//    private User user;
//
//    @ManyToOne
//    @JoinColumn(name = "rule_id", referencedColumnName = "id")
//    private Rule rule;

    @Builder
    public Room(String id, String name, LocalDateTime created_at, String user_id, String video_src, String thumbnail_src, String script, String rule_id){
        this.id = id;
        this.name = name;
        this.created_at = created_at;
        this.user_id = user_id;
        this.video_src = video_src;
        this.thumbnail_src = thumbnail_src;
        this.script = script;
        this.rule_id = rule_id;
    }

}
