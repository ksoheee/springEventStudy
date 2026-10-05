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
@Table(name = "MARKET_CART_ITEM")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CartItem extends BaseIdAndTime {
    @ManyToOne(fetch = FetchType.LAZY)
    private Cart cart;
    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;
}
