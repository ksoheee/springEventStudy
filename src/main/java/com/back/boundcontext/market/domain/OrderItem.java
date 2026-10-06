package com.back.boundcontext.market.domain;

import com.back.global.jpa.entity.BaseIdAndTime;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "MARKET_ORDER_ITEM")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem extends BaseIdAndTime {
    @ManyToOne(fetch = FetchType.LAZY)
    private Order order;
    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    private String productName;
    private long price;
    private long salePrice;
    private double payoutRate = MarketPolicy.PRODUCT_PAYOUT_RATE;

    public OrderItem(Order order, Product product, String productName, long price, long salePrice) {
        this.order = order;
        this.product = product;
        this.productName = productName;
        this.price = price;
        this.salePrice = salePrice;
    }
}
