package com.back.boundcontext.payout.app;

import com.back.boundcontext.payout.domain.PayoutCandidateItem;
import com.back.boundcontext.payout.domain.PayoutEventType;
import com.back.boundcontext.payout.domain.PayoutMember;
import com.back.boundcontext.payout.out.PayoutCandidateItemRepository;
import com.back.shared.market.dto.OrderDto;
import com.back.shared.market.dto.OrderItemDto;
import com.back.shared.market.out.MarketApiClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class PayoutAddPayoutCandidateItemsUseCase {
    private final MarketApiClient marketApiClient;
    private final PayoutCandidateItemRepository payoutCandidateItemRepository;
    private final PayoutSupport payoutSupport;

    public void addPayoutCandidateItems(OrderDto order) {
        //orderItem마다 정산후보 생성
        marketApiClient.getOrderItem(order.getId())
                .forEach(orderItem -> marketPayoutCandidateItems(order, orderItem));
    }

    private void marketPayoutCandidateItems(OrderDto order, OrderItemDto orderItem) {
        PayoutMember system = payoutSupport.findSystemMember().get();
        PayoutMember seller = payoutSupport.findMemberById(orderItem.getSellerId()).get();
        PayoutMember buyer = payoutSupport.findMemberById(orderItem.getBuyerId()).get();

        makePayoutCandidateItem(
                PayoutEventType.정산_상품판매_수수료,
                orderItem.getModelTypeCode(),
                orderItem.getId(),
                order.getPaymentDate(),
                buyer,
                system,
                orderItem.getPayoutFee()
        );

        makePayoutCandidateItem(
                PayoutEventType.정산_상품판매_대금,
                orderItem.getModelTypeCode(),
                orderItem.getId(),
                order.getPaymentDate(),
                buyer,
                seller,
                orderItem.getSalePriceWithoutFee()
        );
    }

    private void makePayoutCandidateItem(
            PayoutEventType eventType,
            String relTypeCode,
            Long relId,
            LocalDateTime paymentDate,
            PayoutMember payer,
            PayoutMember payee,
            long amount
    ){
        PayoutCandidateItem payoutCandidateItem = new PayoutCandidateItem(
            eventType, relTypeCode, relId, paymentDate, payer, payee, amount
        );
        payoutCandidateItemRepository.save(payoutCandidateItem);
    }
}
