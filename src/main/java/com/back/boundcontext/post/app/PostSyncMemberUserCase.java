package com.back.boundcontext.post.app;

import com.back.boundcontext.post.domain.PostMember;
import com.back.boundcontext.post.out.PostMemberRepository;
import com.back.global.evnetpublisher.EventPublisher;
import com.back.shared.member.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostSyncMemberUserCase {
    private final PostMemberRepository postMemberRepository;

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
