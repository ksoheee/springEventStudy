package com.back.boundcontext.member.app;

import com.back.boundcontext.member.domain.Member;
import com.back.boundcontext.member.out.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberSupport {
    private final MemberRepository memberRepository;

    public long count(){
        return memberRepository.count();
    }

    public Optional<Member> findById(Long id) {
        return memberRepository.findById(id);
    }

    public Optional<Member> findByUsername(String username) {
        return memberRepository.findByUsername(username);
    }
}
