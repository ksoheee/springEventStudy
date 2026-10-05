package com.back.boundcontext.member.app;

import com.back.boundcontext.member.domain.Member;
import com.back.boundcontext.member.domain.MemberPolicy;
import com.back.global.rsData.RsData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberFacade {
    private final MemberPolicy memberPolicy;
    private final MemberSupport memberSupport;
    private final MemberCreateUseCase memberCreateUseCase;
    private final MemberGetRandomSecureTipUseCase memberGetRandomSecureTipUseCase;

    @Transactional
    public RsData<Member> create(String username, String password, String nickname) {
        return memberCreateUseCase.create(username, password, nickname);
    }

    public String getRandomSecureTip(){
        return memberGetRandomSecureTipUseCase.getRandomSecureTip();
    }

    @Transactional(readOnly = true)
    public long count(){
        return memberSupport.count();
    }

    @Transactional
    public Optional<Member> findById(Long id) {
        return memberSupport.findById(id);
    }

    @Transactional
    public Optional<Member> findByUsername(String username) {
        return memberSupport.findByUsername(username);
    }


}
