package com.example.mutsasns.repository;

import com.example.mutsasns.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
