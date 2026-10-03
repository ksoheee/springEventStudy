package com.back.shared.initData;

import com.back.boundcontext.member.app.MemberFacade;
import com.back.boundcontext.member.domain.Member;
import com.back.boundcontext.post.app.PostFacade;
import com.back.boundcontext.post.domain.PostMember;
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

        postFacade.write(user1, "user1title1","content1");
        postFacade.write(user1, "user1title2","content1");
        postFacade.write(user1, "user1title3","content1");

        postFacade.write(user2, "user2title1","content2");
        postFacade.write(user2, "user2title2","content2");

        postFacade.write(user3, "user3title1","content1");

    }
}
