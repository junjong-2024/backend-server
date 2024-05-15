package com.debait.debait.user.entity;
// entity -> DB에서 특정한 개체 또는 객체를 나타냄. 데이터베이스 테이블에 매핑된다.
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class User {
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "system-uuid") // DB의 기본 키를 자동 생성
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

    @Column(name = "name")
    private String name;

    @Getter
    @Column(name = "login_id" , length = 50)
    private String login_id;

    @Column(name = "password", length = 100)
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

    @Getter
    @ManyToMany
    @JoinTable(
            name = "user_authority",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "authority_name")
    )
    private Set<Authority> authorities;  // 권한 정보 추가

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
