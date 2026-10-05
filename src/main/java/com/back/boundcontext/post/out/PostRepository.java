package com.back.boundcontext.post.out;

import com.back.boundcontext.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByOrderByIdDesc();
}
