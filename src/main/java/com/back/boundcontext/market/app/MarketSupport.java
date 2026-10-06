package com.back.boundcontext.market.app;

import com.back.boundcontext.market.domain.Cart;
import com.back.boundcontext.market.domain.MarketMember;
import com.back.boundcontext.market.domain.Product;
import com.back.boundcontext.market.out.CartRepository;
import com.back.boundcontext.market.out.MarketMemberRepository;
import com.back.boundcontext.market.out.OrderRepository;
import com.back.boundcontext.market.out.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MarketSupport {
    private final MarketMemberRepository marketMemberRepository;
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;

    public long productsCount(){
        return productRepository.count();
    }

    public long orderCount() {
        return orderRepository.count();
    }

    public Optional<MarketMember> findByUsername(String username) {
        return marketMemberRepository.findByUsername(username);
    }

    public Optional<Cart> findCartByBuyer(MarketMember buyer) {
        return cartRepository.findByBuyer(buyer);
    }

    public Optional<Product> findProductById(Long productId){
        return productRepository.findById(productId);
    }
}
