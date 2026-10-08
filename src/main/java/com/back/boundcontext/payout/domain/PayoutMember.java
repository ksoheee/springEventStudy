package com.back.boundcontext.payout.domain;

import com.back.shared.member.domain.ReplicaMember;
import com.back.shared.payout.dto.PayoutMemberDto;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "PAYOUT_MEMBER")
@Getter
@NoArgsConstructor
public class PayoutMember extends ReplicaMember {
    public PayoutMember(Long id, LocalDateTime createdDate, LocalDateTime modifiedDate,
                        String username, String password, String nickname, int activityScore){
        super(id, createdDate, modifiedDate, username, password, nickname, activityScore);
    }

    public PayoutMemberDto toDto(){
        return new PayoutMemberDto(
                getId(),
                getCreatedDate(),
                getModifiedDate(),
                getUsername(),
                getNickname(),
                getActivityScore()
        );
    }

}
