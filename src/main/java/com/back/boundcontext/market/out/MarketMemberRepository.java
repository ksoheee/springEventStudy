package com.back.boundcontext.market.out;

import com.back.boundcontext.market.domain.MarketMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MarketMemberRepository extends JpaRepository<MarketMember,Long> {
    Optional<MarketMember> findByUsername(String username);
}
