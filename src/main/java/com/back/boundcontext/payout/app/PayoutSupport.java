package com.back.boundcontext.payout.app;

import com.back.boundcontext.payout.domain.PayoutMember;
import com.back.boundcontext.payout.out.PayoutMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PayoutSupport {
    private final PayoutMemberRepository payoutMemberRepository;

    public Optional<PayoutMember> findSystemMember() {
        return payoutMemberRepository.findByUsername("system");
    }
    public Optional<PayoutMember> findMemberById(Long id){
        return payoutMemberRepository.findById(id);
    }

}
