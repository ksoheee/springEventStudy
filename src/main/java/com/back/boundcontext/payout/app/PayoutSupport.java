package com.back.boundcontext.payout.app;

import com.back.boundcontext.payout.out.PayoutMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PayoutSupport {
    private final PayoutMemberRepository payoutMemberRepository;
}
