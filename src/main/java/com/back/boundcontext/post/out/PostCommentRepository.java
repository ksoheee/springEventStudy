package com.back.boundcontext.post.out;

import com.back.boundcontext.post.domain.PostComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostCommentRepository extends JpaRepository<PostComment, Long> {
}
