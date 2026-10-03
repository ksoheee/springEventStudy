package com.back.boundcontext.post.app;

import com.back.boundcontext.post.domain.Post;
import com.back.boundcontext.post.domain.PostComment;
import com.back.boundcontext.post.domain.PostMember;
import com.back.boundcontext.post.out.PostCommentRepository;
import com.back.boundcontext.post.out.PostMemberRepository;
import com.back.boundcontext.post.out.PostRepository;
import com.back.global.evnetpublisher.EventPublisher;
import com.back.global.rsData.RsData;
import com.back.shared.member.dto.MemberDto;
import com.back.shared.post.dto.PostDto;
import com.back.shared.post.event.PostWriteEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostFacade {
    private final PostSyncMemberUserCase postSyncMemberUserCase;
    private final PostWriteUseCase postWriteUseCase;
    private final PostRepository postRepository;
    private final PostMemberRepository postMemberRepository;


    @Transactional(readOnly = true)
    public long count(){
        return postRepository.count();
    }

    @Transactional
    public PostMember syncMember(MemberDto member) {
        return postSyncMemberUserCase.syncMember(member);
    }

    @Transactional
    public RsData<Post> write(PostMember author, String title, String content){
        return postWriteUseCase.write(author, title, content);
    }

    public Optional<PostMember> findByUsername(String username) {
        return postMemberRepository.findByUsername(username);
    }

    public Optional<Post> findById(Long id) {
        return postRepository.findById(id);
    }
}
