package com.back.boundcontext.payout.app;

import com.back.boundcontext.payout.domain.Payout;
import com.back.boundcontext.payout.domain.PayoutMember;
import com.back.boundcontext.payout.out.PayoutMemberRepository;
import com.back.boundcontext.payout.out.PayoutRepository;
import com.back.shared.payout.dto.PayoutMemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PayoutCreatePayoutUseCase {
    private final PayoutMemberRepository payoutMemberRepository;
    private final PayoutRepository payoutRepository;

    public Payout createPayout(PayoutMemberDto payee) {
        PayoutMember _payee = payoutMemberRepository.getReferenceById(payee.getId());
        Payout payout = payoutRepository.save(new Payout(_payee));
        return payout;
    }
}
