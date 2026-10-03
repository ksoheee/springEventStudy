package com.back.boundcontext.member.app;

import com.back.boundcontext.member.domain.Member;
import com.back.boundcontext.member.out.MemberRepository;
import com.back.global.evnetpublisher.EventPublisher;
import com.back.global.rsData.RsData;
import com.back.shared.member.dto.MemberDto;
import com.back.shared.member.event.MemberCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberFacade {
    private final MemberRepository memberRepository;
    private final EventPublisher eventPublisher;

    @Transactional(readOnly = true)
    public long count(){
        return memberRepository.count();
    }

    @Transactional
    public RsData<Member> create(String username, String password, String nickname) {
        Member member = memberRepository.save(new Member(username, password, nickname));

        eventPublisher.publish(new MemberCreatedEvent(new MemberDto(
                member.getId(),
                member.getCreatedDate(),
                member.getModifiedDate(),
                member.getUsername(),
                member.getPassword(),
                member.getNickname(),
                member.getActivityScore()
        )));

        return new RsData<>("201-1","%d번의 회원이 생성되었습니다.".formatted(member.getId()),member);
    }

}
