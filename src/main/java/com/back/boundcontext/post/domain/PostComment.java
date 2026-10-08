package com.back.boundcontext.post.domain;

import com.back.global.jpa.entity.BaseIdAndTime;
import com.back.shared.post.dto.PostCommentDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "POST_COMMENT")
public class PostComment extends BaseIdAndTime {
    @ManyToOne(fetch = LAZY)
    private Post post;
    @ManyToOne(fetch = LAZY)
    private PostMember author;

    @Column(columnDefinition = "TEXT")
    private String comment;

    public PostComment(Post post, PostMember author, String comment) {
        this.post = post;
        this.author = author;
        this.comment = comment;
    }

    public PostCommentDto toDto() {
        return new PostCommentDto(
                getId(),
                getCreatedDate(),
                getModifiedDate(),
                post.getId(),
                author.getId(),
                author.getNickname(),
                comment
        );
    }
}
