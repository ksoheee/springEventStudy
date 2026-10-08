package com.back.boundcontext.market.app;

import com.back.boundcontext.market.domain.Order;
import com.back.boundcontext.market.out.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MarketCancelOrderRequestPaymentUseCase {
    private final OrderRepository orderRepository;

    public void cancelOrderRequestPayment(Long orderId){
        Order order = orderRepository.findById(orderId).get();
        order.cancelRequestPayment(); //결제 실패 -> 주문 취소
    }
}
