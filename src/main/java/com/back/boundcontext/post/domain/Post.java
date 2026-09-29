package com.back.boundcontext.post.domain;

import com.back.global.jpa.entity.BaseIdAndTime;
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

    public PostComment addComment(PostMember author, String comment) {
        PostComment postComment = new PostComment(this, author, comment);
        comments.add(postComment);
        //TODO: 댓글을 달면 이벤트 발행 해야함 이벤트 쏘면 -> member에서 수신
        return postComment;
    }

}
