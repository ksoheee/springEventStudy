package com.back.boundcontext.payout.app;

import com.back.shared.payout.dto.PayoutMemberDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PayoutCreatePayoutUseCase {
    public void createPayout(PayoutMemberDto payee) {
        log.debug("createPayout.payee: {}", payee.getId());
    }
}
