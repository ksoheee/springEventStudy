package com.back.boundcontext.market.out;

import com.back.boundcontext.market.domain.Cart;
import com.back.boundcontext.market.domain.MarketMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByBuyer(MarketMember buyer);
}
