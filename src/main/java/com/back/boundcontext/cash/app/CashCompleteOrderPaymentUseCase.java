package com.back.boundcontext.cash.app;

import com.back.boundcontext.cash.domain.CashLog;
import com.back.boundcontext.cash.domain.Wallet;
import com.back.global.evnetpublisher.EventPublisher;
import com.back.shared.cash.event.CashOrderPaymentFailedEvent;
import com.back.shared.cash.event.CashOrderPaymentSucceededEvent;
import com.back.shared.market.dto.OrderDto;
import com.back.shared.market.event.MarketOrderPaymentRequestedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CashCompleteOrderPaymentUseCase {
    private final CashSupport cashSupport;
    private final EventPublisher eventPublisher;

    public void handle(MarketOrderPaymentRequestedEvent event) {
        Wallet customerWallet = cashSupport.findWalletByHodlerId(event.getOrder().getCustomerId()).get();
        Wallet holdingWallet = cashSupport.findHoldingWallet().get();

        if(event.getPgPaymentAmount()>0){
            customerWallet.credit(event.getPgPaymentAmount(), CashLog.EventType.충전__PG결제_토스페이먼츠, "Order",event.getOrder().getId());
        }

        boolean canPay = customerWallet.getBalance() >= event.getOrder().getSalePrice();

        if(canPay){
            customerWallet.debit(
                    event.getOrder().getSalePrice(),
                    CashLog.EventType.사용_주문결제,
                    "Order",
                    event.getOrder().getId()
                    );

            holdingWallet.credit(
                    event.getOrder().getSalePrice(),
                    CashLog.EventType.임시보관__주문결제,
                    "Order",
                    event.getOrder().getId()
            );

            eventPublisher.publish(new CashOrderPaymentSucceededEvent(
                    new OrderDto(
                            event.getOrder().getId(),
                            event.getOrder().getCreatedDate(),
                            event.getOrder().getModifiedDate(),
                            event.getOrder().getCustomerId(),
                            event.getOrder().getCustomerName(),
                            event.getOrder().getPrice(),
                            event.getOrder().getSalePrice(),
                            event.getOrder().getRequestedPaymentDate(),
                            event.getOrder().getPaymentDate()
                    ), event.getPgPaymentAmount()
            ));
        }else{
            eventPublisher.publish(new CashOrderPaymentFailedEvent(
                    "400-1",
                    "충전은 완료했지만 %d번 주문을 결제완료처리를 하기에는 예치금이 부족합니다.".formatted(event.getOrder().getId()),
                    new OrderDto(
                            event.getOrder().getId(),
                            event.getOrder().getCreatedDate(),
                            event.getOrder().getModifiedDate(),
                            event.getOrder().getCustomerId(),
                            event.getOrder().getCustomerName(),
                            event.getOrder().getPrice(),
                            event.getOrder().getSalePrice(),
                            event.getOrder().getRequestedPaymentDate(),
                            event.getOrder().getPaymentDate()
                    ),
                    event.getPgPaymentAmount(),
                    event.getOrder().getSalePrice()- customerWallet.getBalance()
            ));
        }
    }
}
