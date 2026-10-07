package com.back.boundcontext.cash.domain;

import org.springframework.beans.factory.annotation.Value;

public class CashPolicy {
    public static Long HOLDING_MEMBER_ID;

    @Value("${custom.global.holdingMemberId}")
    public void setHoldingMemberId(Long id){
        HOLDING_MEMBER_ID = id;
    }
}
