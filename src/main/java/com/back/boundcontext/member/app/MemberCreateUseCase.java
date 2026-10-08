package com.back.boundcontext.member.app;

import com.back.boundcontext.member.domain.Member;
import com.back.boundcontext.member.out.MemberRepository;
import com.back.global.evnetpublisher.EventPublisher;
import com.back.global.exception.DomainException;
import com.back.global.rsData.RsData;
import com.back.shared.member.event.MemberCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberCreateUseCase {
    private final MemberRepository memberRepository;
    private final EventPublisher eventPublisher;

    public RsData<Member> create(String username, String password, String nickname){
        memberRepository.findByUsername(username)
                .ifPresent(m-> {
                    throw new DomainException("409-1","이미 존해하는 username입니다.");
                });
        Member member = memberRepository.save(new Member(username, password, nickname));

        eventPublisher.publish(new MemberCreatedEvent(member.toDto()));
        return new RsData<>("201-1","%d번의 회원이 생성되었습니다.".formatted(member.getId()),member);

    }

}
