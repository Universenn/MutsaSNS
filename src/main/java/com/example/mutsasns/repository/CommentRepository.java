package com.example.mutsasns.repository;

import com.example.mutsasns.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    // spring data jpa 쿼리 메소드 생성 시 규칙
    // query 어노테이션에 대해서 공부해봐야겠다.
    Page<Comment> findAllByPostId(Long postId, Pageable pageable);
}
