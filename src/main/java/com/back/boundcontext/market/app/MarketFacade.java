package com.back.boundcontext.market.app;

import com.back.boundcontext.market.domain.MarketMember;
import com.back.shared.member.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MarketFacade {
    private final MarketSyncMemberUseCase marketSyncMemberUseCase;

    @Transactional
    public MarketMember syncMember(MemberDto member) {
       return marketSyncMemberUseCase.syncMember(member);
    }
}
