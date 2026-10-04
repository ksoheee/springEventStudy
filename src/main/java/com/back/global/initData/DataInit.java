package com.back.global.initData;

import com.back.boundcontext.member.app.MemberFacade;
import com.back.boundcontext.member.domain.Member;
import com.back.boundcontext.post.app.PostFacade;
import com.back.boundcontext.post.domain.Post;
import com.back.boundcontext.post.domain.PostComment;
import com.back.boundcontext.post.domain.PostMember;
import com.back.global.rsData.RsData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.transaction.annotation.Transactional;

@Configuration
@Slf4j
public class DataInit {
    private DataInit self;
    private final MemberFacade memberFacade;
    private final PostFacade postFacade;

    public DataInit(
            @Lazy DataInit self,
            MemberFacade memberFacade,
            PostFacade postFacade
    ){
        this.self = self;
        this.memberFacade = memberFacade;
        this.postFacade = postFacade;
    }

    @Bean
    public ApplicationRunner DataInitApplicatinRunner(){
        return args -> {
            self.createBaseMember();
            self.createPost();
            self.createComment();
        };
    }

    @Transactional
    public void createBaseMember(){

        Member systemMember = memberFacade.create("system","pwd","system").getData();
        Member holdingMember = memberFacade.create("holding","pwd","holding").getData();
        Member adminMember = memberFacade.create("admin","pwd","admin").getData();
        Member user1Member = memberFacade.create("user1","pwd","user1").getData();
        Member user2Member = memberFacade.create("user2","pwd","user2").getData();
        Member user3Member = memberFacade.create("user3","pwd","user3").getData();

    }

    @Transactional
    public void createPost(){
        PostMember user1 = postFacade.findByUsername("user1").get();
        PostMember user2 = postFacade.findByUsername("user2").get();
        PostMember user3 = postFacade.findByUsername("user3").get();

        RsData<Post> post1 = postFacade.write(user1, "user1title1","content1");
        log.debug(post1.getMsg());
        RsData<Post> post2 = postFacade.write(user1, "user1title2","content1");
        log.debug(post2.getMsg());
        RsData<Post> post3 = postFacade.write(user1, "user1title3","content1");
        log.debug(post3.getMsg());

        RsData<Post> post4 = postFacade.write(user2, "user2title1","content2");
        log.debug(post4.getMsg());
        RsData<Post> post5 = postFacade.write(user2, "user2title2","content2");
        log.debug(post5.getMsg());

        RsData<Post> post6 = postFacade.write(user3, "user3title1","content1");
        log.debug(post6.getMsg());

    }

    @Transactional
    public void createComment(){
        Post post1 = postFacade.findById(1L).get();
        Post post2 = postFacade.findById(4L).get();
        Post post3 = postFacade.findById(6L).get();

        PostComment comment1 = post1.addComment(post3.getAuthor(), "comment1");
        PostComment comment2 = post2.addComment(post2.getAuthor(), "comment2");
        PostComment comment3 = post3.addComment(post1.getAuthor(), "comment3");

    }
}
