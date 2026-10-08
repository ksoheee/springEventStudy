package com.back.boundcontext.market.app;

import com.back.boundcontext.market.domain.Order;
import com.back.boundcontext.market.out.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MarketCompleteOrderPaymentUseCase {
    private final OrderRepository orderRepository;

    public void completeOrderPayment(Long orderId) {
        Order order = orderRepository.findById(orderId).get();
        order.completePayment(); //결제 성공 시각 기록
    }
}
