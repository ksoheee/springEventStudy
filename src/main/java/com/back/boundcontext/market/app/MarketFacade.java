package com.back.boundcontext.market.app;

import com.back.boundcontext.market.domain.Cart;
import com.back.boundcontext.market.domain.MarketMember;
import com.back.boundcontext.market.domain.Order;
import com.back.boundcontext.market.domain.Product;
import com.back.global.rsData.RsData;
import com.back.shared.cash.event.CashOrderPaymentFailedEvent;
import com.back.shared.cash.event.CashOrderPaymentSucceededEvent;
import com.back.shared.market.dto.MarketMemberDto;
import com.back.shared.member.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MarketFacade {
    private final MarketSupport marketSupport;
    private final MarketSyncMemberUseCase marketSyncMemberUseCase;
    private final MarketProductCreateUseCase marketProductCreateUseCase;
    private final MarketCartCreateUseCase marketCartCreateUseCase;
    private final MarketOrderCreateUseCase marketOrderCreateUseCase;
    private final MarketCompleteOrderPaymentUseCase marketCompleteOrderPaymentUseCase;
    private final MarketCancelOrderRequestPaymentUseCase marketCancelOrderRequestPaymentUseCase;

    @Transactional
    public MarketMember syncMember(MemberDto member) {
       return marketSyncMemberUseCase.syncMember(member);
    }

    @Transactional
    public Product createProduct(MarketMember seller, String sourceType, Long sourceId, String name, String description, long price, long salePrcie){
        return marketProductCreateUseCase.createProduct(seller, sourceType, sourceId, name, description, price, salePrcie);
    }

    @Transactional
    public RsData<Cart> createCart(MarketMemberDto member){
        return marketCartCreateUseCase.createCart(member);
    }

    @Transactional
    public RsData<Order> createOrder(Cart cart){
        return marketOrderCreateUseCase.createOrder(cart);
    }

    @Transactional
    public RsData<Order> createOrder(MarketMember buyer, Product product){
        return marketOrderCreateUseCase.createOrder(buyer, product);
    }

    @Transactional(readOnly = true)
    public long productsCount(){
        return marketSupport.productsCount();
    }

    @Transactional(readOnly = true)
    public long ordersCount(){
        return marketSupport.orderCount();
    }

    @Transactional(readOnly = true)
    public Optional<MarketMember> findByUsername(String username){
        return marketSupport.findByUsername(username);
    }

    @Transactional
    public Optional<Cart> findCartByBuyer(MarketMember buyer){
        return marketSupport.findCartByBuyer(buyer);
    }

    @Transactional
    public Optional<Order> findOrderById(Long id){
        return marketSupport.findOrderById(id);
    }


    @Transactional
    public void requestPayment(Order order, long pgPaymentAmount){
        order.requestPayment(pgPaymentAmount);
    }


    @Transactional
    public Optional<Product> findProductById(Long productId){
        return marketSupport.findProductById(productId);
    }

    @Transactional
    public void handle(CashOrderPaymentSucceededEvent event) {
        marketCompleteOrderPaymentUseCase.handle(event);
    }

    @Transactional
    public void handle(CashOrderPaymentFailedEvent event) {
        marketCancelOrderRequestPaymentUseCase.handle(event);
    }
}
