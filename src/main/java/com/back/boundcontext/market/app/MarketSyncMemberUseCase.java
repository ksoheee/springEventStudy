package com.back.boundcontext.market.app;

import com.back.boundcontext.market.domain.MarketMember;
import com.back.boundcontext.market.out.MarketMemberRepository;
import com.back.global.evnetpublisher.EventPublisher;
import com.back.shared.market.dto.MarketMemberDto;
import com.back.shared.market.event.MarketMemberCreatedEvent;
import com.back.shared.member.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MarketSyncMemberUseCase {
    private final MarketMemberRepository marketMemberRepository;
    private final EventPublisher eventPublisher;

    public MarketMember syncMember(MemberDto member) {
        boolean isNew = !marketMemberRepository.existsById(member.getId());

        MarketMember marketMember = new MarketMember(
                member.getId(),
                member.getCreatedDate(),
                member.getModifiedDate(),
                member.getUsername(),
                "",
                member.getNickname(),
                member.getActivityScore()
        );
        if(isNew){
            eventPublisher.publish(new MarketMemberCreatedEvent(new MarketMemberDto(
                    marketMember.getId(),
                    marketMember.getCreatedDate(),
                    marketMember.getModifiedDate(),
                    marketMember.getUsername(),
                    marketMember.getNickname(),
                    marketMember.getActivityScore()
            )));
        }

        return marketMemberRepository.save(marketMember);
    }
}
