package com.example.mutsasns.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Post {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String body;

    @ManyToOne
//    @JoinColumn(name = "user_id")
    private User user;

    @CreatedDate @Column(nullable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate @Column(nullable = false)
    private LocalDateTime lastModifiedBy;

    public void update(String title, String body) {
        this.title = title;
        this.body = body;
    }
}
