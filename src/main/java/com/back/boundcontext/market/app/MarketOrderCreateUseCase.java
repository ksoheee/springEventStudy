package com.back.boundcontext.market.app;

import com.back.boundcontext.market.domain.Cart;
import com.back.boundcontext.market.domain.MarketMember;
import com.back.boundcontext.market.domain.Order;
import com.back.boundcontext.market.domain.Product;
import com.back.boundcontext.market.out.OrderRepository;
import com.back.global.rsData.RsData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MarketOrderCreateUseCase {
    private final OrderRepository orderRepository;

    public RsData<Order> createOrder(Cart cart){
        Order order = new Order(cart);
        orderRepository.save(order);

        cart.clearItems();

        return new RsData<>("201-1","%d번의 주문이 생성되었습니다.".formatted(order.getId()), order);
    }

    public RsData<Order> createOrder(MarketMember buyer, Product product){
        Order order = new Order(buyer, product);
        orderRepository.save(order);

        return new RsData<>("201-1","%d번의 주문이 생성되었습니다.".formatted(order.getId()), order);
    }
}
