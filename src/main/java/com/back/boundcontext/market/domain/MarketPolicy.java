package com.back.boundcontext.market.domain;

import org.springframework.beans.factory.annotation.Value;

public class MarketPolicy {
    public static double PRODUCT_PAYOUT_RATE;

    @Value("${custom.market.product.payoutRate}")
    public void setPRODUCT_PAYOUT_RATE(double rate) {
        PRODUCT_PAYOUT_RATE = rate;
    }
}
