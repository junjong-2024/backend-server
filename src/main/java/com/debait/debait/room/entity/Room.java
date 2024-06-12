package com.debait.debait.room.entity;

import com.debait.debait.rule.entity.Rule;
import com.debait.debait.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;

@Entity
@Table(name = "discussion_room")
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Room {
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name="description")
    private String description;

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
    @JoinColumn(name = "rule_id", referencedColumnName = "id", nullable = false)
    private Rule rule;

    @PrePersist
    protected void onCreated() {
        created_at = LocalDateTime.now();
    }

    @Builder
    public Room(String id, String name, String description, LocalDateTime created_at, User user, String video_src, String thumbnail_src, String script, Rule rule) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.created_at = created_at;
        this.user = user;
        this.video_src = video_src;
        this.thumbnail_src = thumbnail_src;
        this.script = script;
        this.rule = rule;
    }
}
