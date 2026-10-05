package com.back.boundcontext.market.out;

import com.back.boundcontext.market.domain.MarketMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MarketMemberRepository extends JpaRepository<MarketMember,Long> {
}
