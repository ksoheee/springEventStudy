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
        //TODO: 복사 member들에게도 활동점수 알려줘야 하므로 이벤트 발행
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
