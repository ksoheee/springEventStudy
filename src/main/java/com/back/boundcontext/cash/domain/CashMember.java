package com.back.boundcontext.cash.domain;

import com.back.shared.cash.dto.CashMemberDto;
import com.back.shared.member.domain.ReplicaMember;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name ="CASH_MEMBER")
@Getter
@NoArgsConstructor
public class CashMember extends ReplicaMember {
    public CashMember(Long id, LocalDateTime createdDate, LocalDateTime modifiedDate,
                      String username, String password, String nickname, int activityScore) {
        super(id, createdDate, modifiedDate, username, password, nickname, activityScore);
    }
    public CashMemberDto toDto(){
        return new CashMemberDto(
                getId(),
                getCreatedDate(),
                getModifiedDate(),
                getUsername(),
                getNickname(),
                getActivityScore()
        );
    }
}
