package com.back.boundcontext.market.app;

import com.back.boundcontext.market.domain.Cart;
import com.back.boundcontext.market.domain.MarketMember;
import com.back.boundcontext.market.out.CartRepository;
import com.back.boundcontext.market.out.MarketMemberRepository;
import com.back.global.rsData.RsData;
import com.back.shared.market.dto.MarketMemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MarketCartCreateUseCase {
    private final MarketMemberRepository marketMemberRepository;
    private final CartRepository cartRepository;

    public RsData<Cart> createCart(MarketMemberDto buyer) {
        MarketMember _buyer =  marketMemberRepository.getReferenceById(buyer.getId());
        Cart cart = new Cart(_buyer);
        cartRepository.save(cart);

        return new RsData<>("201-1","장바구니가 생성되었습니다.",cart);
    }
}
