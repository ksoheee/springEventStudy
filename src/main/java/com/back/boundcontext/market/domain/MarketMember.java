package com.back.boundcontext.market.domain;

import com.back.shared.market.dto.MarketMemberDto;
import com.back.shared.member.domain.ReplicaMember;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "MARKET_MEMBER")
@Getter
@NoArgsConstructor
public class MarketMember extends ReplicaMember {
    public MarketMember(Long id, LocalDateTime createdDate, LocalDateTime modifiedDate,
                        String username, String password, String nickname, int activityScore){
        super(id, createdDate, modifiedDate, username, password, nickname, activityScore);
    }

    public MarketMemberDto toDto(){
        return new MarketMemberDto(
                getId(),
                getCreatedDate(),
                getModifiedDate(),
                getUsername(),
                getNickname(),
                getActivityScore()
        );
    }


}
