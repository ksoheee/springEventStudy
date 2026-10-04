package com.back.boundcontext.post.app;

import com.back.boundcontext.post.domain.Post;
import com.back.boundcontext.post.domain.PostMember;
import com.back.boundcontext.post.out.PostRepository;
import com.back.global.evnetpublisher.EventPublisher;
import com.back.global.rsData.RsData;
import com.back.shared.member.out.MemberApiClient;
import com.back.shared.post.dto.PostDto;
import com.back.shared.post.event.PostWriteEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostWriteUseCase {
    private final PostRepository postRepository;
    private final EventPublisher eventPublisher;
    private final MemberApiClient memberApiClient;

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
        String randomSecureTip = memberApiClient.getRandomSecureTip();

        return new RsData<>("201-1",
                "%d번 글이 생성되었습니다. 보안 팁: %s".formatted(post.getId(),randomSecureTip), post);
    }
}
