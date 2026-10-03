package com.back.boundcontext.member.domain;


import com.back.shared.member.domain.SourceMember;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name="MEMBER_MEMBER")
@NoArgsConstructor
public class Member extends SourceMember {
    public Member(String username, String password, String nickname){
        super(username, password, nickname);
    }

    public void increaseActivityScore(int amount){
        if(amount == 0) return;

        setActivityScore(getActivityScore() + amount);
        //TODO: 댓글작성시 1점 글과 댓글에서 이벤트 발행하면 member에서 받아서 이 메서드 호출해야함
        //TODO: 복사 member들에게도 활동점수 알려줘야 하므로 이벤트 발행


    }

}
