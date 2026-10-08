package com.back.boundcontext.cash.app;

import com.back.boundcontext.cash.domain.CashLog;
import com.back.boundcontext.cash.domain.Wallet;
import com.back.global.evnetpublisher.EventPublisher;
import com.back.shared.cash.event.CashOrderPaymentFailedEvent;
import com.back.shared.cash.event.CashOrderPaymentSucceededEvent;
import com.back.shared.market.dto.OrderDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CashCompleteOrderPaymentUseCase {
    private final CashSupport cashSupport;
    private final EventPublisher eventPublisher;

    public void completeOrderPayment(OrderDto order, long pgPayment) {
        Wallet customerWallet = cashSupport.findWalletByHodlerId(order.getCustomerId()).get();
        Wallet holdingWallet = cashSupport.findHoldingWallet().get();

        if(pgPayment>0){
            customerWallet.credit(pgPayment, CashLog.EventType.충전__PG결제_토스페이먼츠, order.getModelTypeCode(),order.getId());
        }

        boolean canPay = customerWallet.getBalance() >= order.getSalePrice();

        if(canPay){
            customerWallet.debit(
                    order.getSalePrice(),
                    CashLog.EventType.사용_주문결제,
                    order.getModelTypeCode(),
                    order.getId()
                    );

            holdingWallet.credit(
                   order.getSalePrice(),
                    CashLog.EventType.임시보관__주문결제,
                    order.getModelTypeCode(),
                    order.getId()
            );

            eventPublisher.publish(new CashOrderPaymentSucceededEvent(
                    new OrderDto(
                            order.getId(),
                            order.getCreatedDate(),
                            order.getModifiedDate(),
                            order.getCustomerId(),
                            order.getCustomerName(),
                            order.getPrice(),
                            order.getSalePrice(),
                            order.getRequestedPaymentDate(),
                            order.getPaymentDate()
                    ), pgPayment
            ));
        }else{
            eventPublisher.publish(new CashOrderPaymentFailedEvent(
                    "400-1",
                    "충전은 완료했지만 %d번 주문을 결제완료처리를 하기에는 예치금이 부족합니다.".formatted(order.getId()),
                    new OrderDto(
                            order.getId(),
                            order.getCreatedDate(),
                            order.getModifiedDate(),
                            order.getCustomerId(),
                            order.getCustomerName(),
                            order.getPrice(),
                            order.getSalePrice(),
                            order.getRequestedPaymentDate(),
                            order.getPaymentDate()
                    ),
                    pgPayment,
                    order.getSalePrice()- customerWallet.getBalance()
            ));
        }
    }
}
