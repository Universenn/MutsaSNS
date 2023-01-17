package com.example.mutsasns.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Comment extends BaseEntity{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_userId")
    private User user;

    @ManyToOne
    @JoinColumn(name = "user_postId")
    private Post post;

    private String comment;


    public void update(String comment) {
        this.comment = comment;
    }
}
