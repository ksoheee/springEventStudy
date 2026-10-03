package com.back.boundcontext.post.out;

import com.back.boundcontext.post.domain.PostMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostMemberRepository extends JpaRepository<PostMember, Long> {
}
