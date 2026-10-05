package com.back.boundcontext.cash.app;

import com.back.boundcontext.cash.domain.CashMember;
import com.back.boundcontext.cash.out.CashMemberRepository;
import com.back.global.evnetpublisher.EventPublisher;
import com.back.shared.cash.dto.CashMemberDto;
import com.back.shared.cash.event.CashMemberCreatedEvent;
import com.back.shared.member.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CashSyncMemberUserCase {
    private final CashMemberRepository cashMemberRepository;
    private final EventPublisher eventPublisher;

    public CashMember syncMember(MemberDto member){
        //해당 id를 가진 data가 DB에 존재하는지 확인
        boolean isNew = !cashMemberRepository.existsById(member.getId());

        CashMember cashMember = new CashMember(
                member.getId(),
                member.getCreatedDate(),
                member.getModifiedDate(),
                member.getUsername(),
                "",
                member.getNickname(),
                member.getActivityScore()
        );
        cashMemberRepository.save(cashMember);

        if(isNew){
            eventPublisher.publish(new CashMemberCreatedEvent(new CashMemberDto(
                    cashMember.getId(),
                    cashMember.getCreatedDate(),
                    cashMember.getModifiedDate(),
                    cashMember.getUsername(),
                    cashMember.getNickname(),
                    cashMember.getActivityScore()
            )));
        }
        return cashMember;
    }
}
