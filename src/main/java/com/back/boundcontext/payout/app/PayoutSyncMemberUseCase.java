package com.back.boundcontext.payout.app;

import com.back.boundcontext.payout.domain.PayoutMember;
import com.back.boundcontext.payout.out.PayoutMemberRepository;
import com.back.global.evnetpublisher.EventPublisher;
import com.back.shared.member.dto.MemberDto;
import com.back.shared.payout.event.PayoutMemberCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PayoutSyncMemberUseCase {
    private final PayoutMemberRepository payoutMemberRepository;
    private final EventPublisher eventPublisher;

    public PayoutMember syncMember(MemberDto member) {
        boolean isNew = !payoutMemberRepository.existsById(member.getId());

        PayoutMember _member = payoutMemberRepository.save(
                new PayoutMember(
                    member.getId(),
                    member.getCreatedDate(),
                    member.getModifiedDate(),
                    member.getUsername(),
                    "",
                    member.getNickname(),
                    member.getActivityScore()
                )
        );

        if(isNew){
            eventPublisher.publish(new PayoutMemberCreatedEvent(_member.toDto()));
        }
        return _member;

    }
}
