package com.back.boundcontext.member.app;

import com.back.boundcontext.member.domain.Member;
import com.back.boundcontext.member.out.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberFacade {
    private final MemberRepository memberRepository;


    public Member create(String username, String password, String nickname) {
        Member member = new Member(username, password, nickname);
        //TODO: 회원가입시 복제를 위해 이벤트 발생해야 함

        return memberRepository.save(member);
    }

}
