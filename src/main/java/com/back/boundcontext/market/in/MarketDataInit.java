package com.back.boundcontext.market.in;

import com.back.boundcontext.market.app.MarketFacade;
import com.back.boundcontext.market.domain.Cart;
import com.back.boundcontext.market.domain.MarketMember;
import com.back.boundcontext.market.domain.Order;
import com.back.boundcontext.market.domain.Product;
import com.back.global.rsData.RsData;
import com.back.shared.post.dto.PostDto;
import com.back.shared.post.out.PostApiClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Slf4j
public class MarketDataInit {
    private final MarketDataInit self;
    private final MarketFacade marketFacade;
    private final PostApiClient postApiClient;

    public MarketDataInit(
            @Lazy  MarketDataInit self, MarketFacade marketFacade, PostApiClient postApiClient
    ) {
        this.self = self;
        this.marketFacade = marketFacade;
        this.postApiClient = postApiClient;
    }

    @Bean
    @org.springframework.core.annotation.Order(3)
    public ApplicationRunner marketDataInitApplicationRunner() {
        return args -> {
            self.makeBaseProducts();
            self.makeBaseCartItems();
            self.makeBBaseOrders();
        };
    }

    @Transactional
    public void makeBaseProducts(){
        if(marketFacade.productsCount() > 0) return;

        List<PostDto> posts = postApiClient.getItems();

        PostDto post1 = posts.get(5);
        PostDto post2 = posts.get(4);
        PostDto post3 = posts.get(3);
        PostDto post4 = posts.get(2);
        PostDto post5 = posts.get(1);
        PostDto post6 = posts.get(0);

        MarketMember marketMember1 = marketFacade.findByUsername("user1").get();
        MarketMember marketMember2 = marketFacade.findByUsername("user2").get();
        MarketMember marketMember3 = marketFacade.findByUsername("user3").get();

        Product product1 = marketFacade.createProduct(marketMember1,"Post",post1.getId(),post1.getTitle(),post1.getContent(),10_000,10_000);
        Product product2 = marketFacade.createProduct(marketMember1,"Post",post2.getId(),post2.getTitle(),post2.getContent(),15_000,15_000);
        Product product3 = marketFacade.createProduct(marketMember1,"Post",post3.getId(),post3.getTitle(),post3.getContent(),20_000,20_000);
        Product product4 = marketFacade.createProduct(marketMember2,"Post",post4.getId(),post4.getTitle(),post4.getContent(),25_000,25_000);
        Product product5 = marketFacade.createProduct(marketMember2,"Post",post5.getId(),post5.getTitle(),post5.getContent(),30_000,30_000);
        Product product6 = marketFacade.createProduct(marketMember3,"Post",post6.getId(),post6.getTitle(),post6.getContent(),35_000,35_000);
    }

    @Transactional
    public void makeBaseCartItems(){
        MarketMember buyer1 = marketFacade.findByUsername("user1").get();
        MarketMember buyer2 = marketFacade.findByUsername("user2").get();
        MarketMember buyer3 = marketFacade.findByUsername("user3").get();

        Cart buyer1Cart = marketFacade.findCartByBuyer(buyer1).get();
        Cart buyer2Cart = marketFacade.findCartByBuyer(buyer2).get();
        Cart buyer3Cart = marketFacade.findCartByBuyer(buyer3).get();

        Product product1 = marketFacade.findProductById(1L).get();
        Product product2 = marketFacade.findProductById(2L).get();
        Product product3 = marketFacade.findProductById(3L).get();
        Product product4 = marketFacade.findProductById(4L).get();
        Product product5 = marketFacade.findProductById(5L).get();
        Product product6 = marketFacade.findProductById(6L).get();

        if(buyer1Cart.hasItems()) return;

        buyer1Cart.addItem(product1);
        buyer1Cart.addItem(product2);
        buyer1Cart.addItem(product3);
        buyer1Cart.addItem(product4);

        buyer2Cart.addItem(product1);
        buyer2Cart.addItem(product2);
        buyer2Cart.addItem(product3);

        buyer3Cart.addItem(product1);
        buyer3Cart.addItem(product2);
    }

    @Transactional
    public void makeBBaseOrders(){
        if(marketFacade.ordersCount() > 0) return;

        MarketMember buyer1 = marketFacade.findByUsername("user1").get();
        MarketMember buyer2 = marketFacade.findByUsername("user2").get();
        MarketMember buyer3 = marketFacade.findByUsername("user3").get();

        Cart buyer1Cart = marketFacade.findCartByBuyer(buyer1).get();
        Cart buyer2Cart = marketFacade.findCartByBuyer(buyer2).get();
        Cart buyer3Cart = marketFacade.findCartByBuyer(buyer3).get();

        Order order1 = marketFacade.createOrder(buyer1Cart).getData();
        Order order2 = marketFacade.createOrder(buyer2Cart).getData();
        Order order3 = marketFacade.createOrder(buyer3Cart).getData();

        Product product1 = marketFacade.findProductById(1L).get();
        Product product2 = marketFacade.findProductById(2L).get();
        Product product3 = marketFacade.findProductById(3L).get();
        Product product4 = marketFacade.findProductById(4L).get();
        Product product5 = marketFacade.findProductById(5L).get();
        Product product6 = marketFacade.findProductById(6L).get();

        buyer1Cart.addItem(product1);
        buyer1Cart.addItem(product2);
        buyer1Cart.addItem(product3);
        buyer1Cart.addItem(product4);


    }


}
