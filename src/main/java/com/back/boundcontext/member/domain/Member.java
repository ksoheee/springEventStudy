package com.back.boundcontext.member.domain;


import com.back.shared.member.domain.SourceMember;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name="MEMBER_MEMBEr")
@NoArgsConstructor
public class Member extends SourceMember {
    public Member(String username, String password, String nickname){
        super(username, password, nickname);
    }

    public void increaseActivityScore(int amount){
        if(amount == 0) return;

        setActivityScore(getActivityScore() + amount);
        //TODO: 글 작성시 3점, 댓글작성시 1점 글과 댓글에서 이벤트 발행하면 member에서 받아서 이 메서드 호출해야함
    }

}
