package com.back.boundcontext.market.domain;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * PRODUCT_PAYOUT_RATE: 현재 기본 정산율
 * payoutRate: 해당 주문 상품에 적용할 정산율
 * 정산율을 매개변수로 받아 주문마다 서로 다른 정산율을 적용할 수 있도록 함.
 * 주문 당시 정산율을 OrderItem에 저장했다면, 이후 기본 정산율이 변경되어도 기존 주문은 당시 정산율로 계산할 수 있음.
 */
@Service
public class MarketPolicy {
    //신규 주문에 적용할 기본 정산율
    public static double PRODUCT_PAYOUT_RATE;

    @Value("${custom.market.product.payoutRate}")
    public void setPRODUCT_PAYOUT_RATE(double rate) {
        PRODUCT_PAYOUT_RATE = rate;
    }

    //플랫폼 수수료 계산
    public static long calculatePayoutFee(long salePrice, double payoutRate){
        return salePrice-calculateSalePriceWithoutFee(salePrice,payoutRate);
    }
    //전달받은 정산율로 정산 금액 계산(판매자가 실제로 받을 금액)
    public static long calculateSalePriceWithoutFee(long salePrice, double payoutRate){
        return Math.round(salePrice*(payoutRate/100));
    }
}
