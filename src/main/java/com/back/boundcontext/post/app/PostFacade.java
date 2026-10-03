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
    private final PostRepository postRepository;
    private final PostMemberRepository postMemberRepository;
    private final PostCommentRepository postCommentRepository;
    private final EventPublisher eventPublisher;

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

    @Transactional
    public RsData<Post> write(PostMember author, String title, String content){
        Post post = postRepository.save(new Post(author, title, content));

        eventPublisher.publish(new PostWriteEvent(new PostDto(
                post.getId(),
                post.getCreatedDate(),
                post.getModifiedDate(),
                post.getAuthor().getId(),
                post.getAuthor().getUsername(),
                post.getTitle(),
                post.getContent()
        )));

        return new RsData<>("201-1","%d번 글이 생성되었습니다.".formatted(post.getId()),post);
    }

    public Optional<PostMember> findByUsername(String username) {
        return postMemberRepository.findByUsername(username);
    }

    public Optional<Post> findById(Long id) {
        return postRepository.findById(id);
    }
}
