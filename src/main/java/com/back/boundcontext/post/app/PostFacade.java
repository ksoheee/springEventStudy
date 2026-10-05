package com.back.boundcontext.post.app;

import com.back.boundcontext.post.domain.Post;
import com.back.boundcontext.post.domain.PostMember;
import com.back.boundcontext.post.out.PostMemberRepository;
import com.back.boundcontext.post.out.PostRepository;
import com.back.global.rsData.RsData;
import com.back.shared.member.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostFacade {
    private final PostSupport postSupport;
    private final PostSyncMemberUserCase postSyncMemberUserCase;
    private final PostWriteUseCase postWriteUseCase;

    @Transactional
    public PostMember syncMember(MemberDto member) {
        return postSyncMemberUserCase.syncMember(member);
    }

    @Transactional
    public RsData<Post> write(PostMember author, String title, String content){
        return postWriteUseCase.write(author, title, content);
    }

    @Transactional(readOnly = true)
    public long count(){
        return postSupport.count();
    }

    @Transactional
    public Optional<PostMember> findByUsername(String username) {
        return postSupport.findByUsername(username);
    }

    @Transactional
    public Optional<Post> findById(Long id) {
        return postSupport.findById(id);
    }
}
