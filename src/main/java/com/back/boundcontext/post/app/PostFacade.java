package com.back.boundcontext.post.app;

import com.back.boundcontext.post.domain.PostMember;
import com.back.boundcontext.post.out.PostMemberRepository;
import com.back.boundcontext.post.out.PostRepository;
import com.back.shared.member.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostFacade {
    private final PostRepository postRepository;
    private final PostMemberRepository postMemberRepository;

    @Transactional(readOnly = true)
    public long count(){
        return postRepository.count();
    }

    @Transactional
    public PostMember syncMember(MemberDto member) {
        PostMember postMember = new PostMember(
                member.getId(),
                member.getCreatedDate(),
                member.getModifiedDate(),
                member.getUsername(),
                "",
                member.getNickname(),
                member.getActivityScore()

        );
        return postMemberRepository.save(postMember);
    }
}
