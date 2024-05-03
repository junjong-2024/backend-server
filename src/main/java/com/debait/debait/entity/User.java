package com.debait.debait.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor
public class User {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "login_id")
    private String login_id;

    @Column(name = "password")
    private String password;

    @Column(name = "user_email")
    private String user_email;

    @Column(name = "payment_at")
    private LocalDateTime payment_at;

    @Column(name = "payment_expires_at")
    private LocalDateTime payment_expires_at;

    @Column(name = "usage_storage")
    private int usage_storage;

    @Column(name = "max_storage")
    private int max_storage;

//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Room> rooms = new ArrayList<>();

    @Builder
    public User(String id, String name, String login_id, String password, String user_email, LocalDateTime payment_at, LocalDateTime payment_expires_at, int usage_storage, int max_stroage){
        this.id = id;
        this.name = name;
        this.login_id = login_id;
        this.password = password;
        this.user_email = user_email;
        this.payment_at = payment_at;
        this.payment_expires_at = payment_expires_at;
        this.usage_storage = usage_storage;
        this.max_storage = max_stroage;
    }
}
