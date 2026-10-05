package com.back.boundcontext.market.app;

import com.back.boundcontext.market.domain.MarketMember;
import com.back.boundcontext.market.domain.Product;
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

    @Transactional
    public MarketMember syncMember(MemberDto member) {
       return marketSyncMemberUseCase.syncMember(member);
    }

    @Transactional
    public Product createProduct(MarketMember seller, String sourceType, Long sourceId, String name, String description, long price, long salePrcie){
        return marketProductCreateUseCase.createProduct(seller, sourceType, sourceId, name, description, price, salePrcie);
    }

    @Transactional(readOnly = true)
    public long productsCount(){
        return marketSupport.productsCount();
    }

    @Transactional(readOnly = true)
    public Optional<MarketMember> findByUsername(String username){
        return marketSupport.findByUsername(username);
    }
}
