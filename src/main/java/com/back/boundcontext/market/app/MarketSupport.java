package com.back.boundcontext.market.app;

import com.back.boundcontext.market.domain.MarketMember;
import com.back.boundcontext.market.out.MarketMemberRepository;
import com.back.boundcontext.market.out.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MarketSupport {
    private final MarketMemberRepository marketMemberRepository;
    private final ProductRepository productRepository;

    public long productsCount(){
        return productRepository.count();
    }

    public Optional<MarketMember> findByUsername(String username) {
        return marketMemberRepository.findByUsername(username);
    }

}
