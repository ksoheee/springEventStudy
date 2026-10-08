package com.back.boundcontext.post.domain;

import com.back.global.jpa.entity.BaseIdAndTime;
import com.back.shared.post.dto.PostCommentDto;
import com.back.shared.post.dto.PostDto;
import com.back.shared.post.event.PostCommentWriteEvent;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name="POST_POST")
@NoArgsConstructor
public class Post extends BaseIdAndTime {
    @ManyToOne(fetch = FetchType.LAZY)
    private PostMember author;

    private String title;
    @Column(columnDefinition = "LONGTEXT")
    private String content;

    @OneToMany(mappedBy ="post", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    private List<PostComment> comments = new ArrayList<>();

    public Post(PostMember author, String title, String content) {
        this.author = author;
        this.title = title;
        this.content = content;
    }

    public PostDto toDto(){
        return new PostDto(
                getId(),
                getCreatedDate(),
                getModifiedDate(),
                author.getId(),
                author.getUsername(),
                title,
                content
        );
    }

    public PostComment addComment(PostMember author, String comment) {
        PostComment postComment = new PostComment(this, author, comment);
        comments.add(postComment);

        publishEvent(new PostCommentWriteEvent(postComment.toDto()));

        return postComment;
    }
    public boolean hasComments(){
        return !comments.isEmpty();
    }

}
