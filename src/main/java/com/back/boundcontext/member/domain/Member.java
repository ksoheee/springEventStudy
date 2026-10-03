package com.back.boundcontext.member.domain;


import com.back.global.evnetpublisher.EventPublisher;
import com.back.global.global.GlobalConfig;
import com.back.shared.member.domain.SourceMember;
import com.back.shared.member.dto.MemberDto;
import com.back.shared.member.event.MemberModifiedEvent;
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
        publishEvent(new MemberModifiedEvent(new MemberDto(
                this.getId(),
                this.getCreatedDate(),
                this.getModifiedDate(),
                this.getUsername(),
                "",
                this.getNickname(),
                this.getActivityScore()
        )));

    }

}
