package com.back.boundcontext.cash.in;

import com.back.boundcontext.cash.app.CashFacade;
import com.back.boundcontext.cash.domain.CashLog;
import com.back.boundcontext.cash.domain.CashMember;
import com.back.boundcontext.cash.domain.Wallet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Slf4j
public class CashDataInit {
    private final CashDataInit self;
    private final CashFacade cashFacade;

    public CashDataInit(
            @Lazy CashDataInit self, CashFacade cashFacade
    ) {
        this.self = self;
        this.cashFacade = cashFacade;
    }

    @Bean
    @Order(2)
    public ApplicationRunner cashDataInitApplicationRunner(){
        return args -> {
            self.makeBaseCredits();
        };
    }

    @Transactional
    public void makeBaseCredits(){
        //어떤 user인지
        CashMember user1 = cashFacade.findMemberByUsername("user1").get();
        CashMember user2 = cashFacade.findMemberByUsername("user2").get();

        //그 유저 바탕으로 지갑을 찾음
        Wallet wallet1 = cashFacade.findWalletByHolder(user1).get();
        Wallet wallet2 = cashFacade.findWalletByHolder(user2).get();

        wallet1.credit(150_000, CashLog.EventType.충전__무통장입금);
        wallet1.credit(100_000, CashLog.EventType.충전__무통장입금);
        wallet1.credit(50_000, CashLog.EventType.충전__무통장입금);

        wallet2.credit(150_000, CashLog.EventType.충전__무통장입금);




    }

}
