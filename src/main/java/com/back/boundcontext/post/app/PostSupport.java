package com.back.boundcontext.post.app;

import com.back.boundcontext.post.domain.Post;
import com.back.boundcontext.post.domain.PostMember;
import com.back.boundcontext.post.out.PostMemberRepository;
import com.back.boundcontext.post.out.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostSupport {
    private final PostRepository postRepository;
    private final PostMemberRepository postMemberRepository;

    public long count(){
        return postRepository.count();
    }
    public Optional<PostMember> findByUsername(String username) {
        return postMemberRepository.findByUsername(username);
    }

    public Optional<Post> findById(Long id) {
        return postRepository.findById(id);
    }
}
