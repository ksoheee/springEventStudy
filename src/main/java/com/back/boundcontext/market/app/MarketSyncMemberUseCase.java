package com.back.boundcontext.market.app;

import com.back.boundcontext.market.domain.MarketMember;
import com.back.boundcontext.market.out.MarketMemberRepository;
import com.back.shared.member.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MarketSyncMemberUseCase {
    private final MarketMemberRepository marketMemberRepository;

    public MarketMember syncMember(MemberDto member) {
        MarketMember marketMember = new MarketMember(
                member.getId(),
                member.getCreatedDate(),
                member.getModifiedDate(),
                member.getUsername(),
                "",
                member.getNickname(),
                member.getActivityScore()
        );
        return marketMemberRepository.save(marketMember);
    }
}
