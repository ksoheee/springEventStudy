package com.back.boundcontext.member.app;

import com.back.boundcontext.member.domain.Member;
import com.back.boundcontext.member.domain.MemberPolicy;
import com.back.boundcontext.member.out.MemberRepository;
import com.back.global.rsData.RsData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberFacade {
    private final MemberCreateUseCase memberCreateUseCase;
    private final MemberRepository memberRepository;
    private final MemberPolicy memberPolicy;

    @Transactional(readOnly = true)
    public long count(){
        return memberRepository.count();
    }
    @Transactional
    public RsData<Member> create(String username, String password, String nickname) {
        return memberCreateUseCase.create(username, password, nickname);
    }

    @Transactional
    public Optional<Member> findById(Long id) {
        return memberRepository.findById(id);
    }

    @Transactional
    public Optional<Member> findByUsername(String username) {
        return memberRepository.findByUsername(username);
    }

    public String getRandomSecureTip(){
        return "비밀번호의 유효기간은 %d입니다.".formatted(memberPolicy.getNeedToChangePasswordDays());
    }
}
