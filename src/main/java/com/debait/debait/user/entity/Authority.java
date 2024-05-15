package com.debait.debait.user.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table (name = "users")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Authority {

    @Id
    @Column (name = "authority_name", length = 50)
    private String authorityName;

    public String getAuthorityName() {
        return authorityName;
    }
}
