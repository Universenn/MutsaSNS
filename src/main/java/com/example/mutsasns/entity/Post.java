package com.example.mutsasns.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.REMOVE;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
@Where(clause = "deleted = false")
@SQLDelete(sql = "UPDATE Post SET deleted = true WHERE id = ?")
public class Post {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String body;

    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id")
    private User user;

    @CreatedDate @Column(nullable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate @Column(nullable = false)
    private LocalDateTime lastModifiedBy;

    @OneToMany(mappedBy = "post",cascade = REMOVE, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "post",cascade = REMOVE, orphanRemoval = true)
    private List<Likes> likes = new ArrayList<>();



    public void update(String title, String body) {
        this.title = title;
        this.body = body;
    }

    private boolean deleted = Boolean.FALSE; // 삭제 여부 기본값 false
}
