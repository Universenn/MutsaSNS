package com.example.mutsasns.repository;

import com.example.mutsasns.entity.Post;
import com.example.mutsasns.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findAllByUser(Pageable pageable, User user);
}
