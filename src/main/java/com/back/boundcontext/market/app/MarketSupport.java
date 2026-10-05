package com.back.boundcontext.market.app;

import com.back.boundcontext.market.out.MarketMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MarketSupport {
    private final MarketMemberRepository marketMemberRepository;

}
