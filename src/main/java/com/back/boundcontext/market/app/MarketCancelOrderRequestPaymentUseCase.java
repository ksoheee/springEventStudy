package com.back.boundcontext.market.app;

import com.back.boundcontext.market.domain.Order;
import com.back.boundcontext.market.out.OrderRepository;
import com.back.shared.cash.event.CashOrderPaymentFailedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MarketCancelOrderRequestPaymentUseCase {
    private final OrderRepository orderRepository;

    public void handle(CashOrderPaymentFailedEvent event) {
        Order order = orderRepository.findById(event.getOrder().getId()).get();
        order.cancelRequestPayment(); //결제 실패 -> 주문 취소
    }
}
