package com.back.boundcontext.payout.app;

import com.back.boundcontext.payout.domain.Payout;
import com.back.boundcontext.payout.domain.PayoutCandidateItem;
import com.back.boundcontext.payout.domain.PayoutMember;
import com.back.global.rsData.RsData;
import com.back.shared.market.dto.OrderDto;
import com.back.shared.member.dto.MemberDto;
import com.back.shared.payout.dto.PayoutMemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PayoutFacade {
    private final PayoutSupport payoutSupport;
    private final PayoutSyncMemberUseCase payoutSyncMemberUseCase;
    private final PayoutCreatePayoutUseCase payoutCreatePayoutUseCase;
    private final PayoutAddPayoutCandidateItemsUseCase payoutAddPayoutCandidateItemsUseCase;
    private final PayoutCollectPayoutItemsMoreUseCase payoutCollectPayoutItemsMoreUseCase;

    @Transactional
    public PayoutMember syncMember(MemberDto member) {
        return payoutSyncMemberUseCase.syncMember(member);
    }

    @Transactional
    public Payout createPayout(PayoutMemberDto payee) {
        return payoutCreatePayoutUseCase.createPayout(payee);
    }

    @Transactional
    public void addPayoutCandidateItems(OrderDto order) {
        payoutAddPayoutCandidateItemsUseCase.addPayoutCandidateItems(order);
    }

    @Transactional
    public RsData<Integer> collectPayoutItemsMore(int limit){
        return payoutCollectPayoutItemsMoreUseCase.collectPayoutItemsMore(limit);
    }

    @Transactional
    public List<PayoutCandidateItem> findPayoutCandidateItems() {
        return payoutSupport.findPayoutCandidateItems();
    }

}
