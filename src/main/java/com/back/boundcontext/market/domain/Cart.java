package com.back.boundcontext.market.domain;

import com.back.global.jpa.entity.BaseManualIdAndTime;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "MARKET_CART")
@Getter
@NoArgsConstructor
public class Cart extends BaseManualIdAndTime {

    @ManyToOne(fetch = FetchType.LAZY)
    private MarketMember buyer;
    @OneToMany(mappedBy = "cart", cascade = {CascadeType.REMOVE,CascadeType.PERSIST},orphanRemoval = true)
    private List<CartItem> items = new ArrayList<>();
    private int itemsCount;

    public Cart(MarketMember buyer) {
        super(buyer.getId());
        this.buyer = buyer;
    }

    public boolean hasItems(){
        return itemsCount>0;
    }

    public void addItem(Product product){
        CartItem cartItem = new CartItem(this, product);
        this.getItems().add(cartItem);
        itemsCount++;
    }
    public void clearItems(){
        this.getItems().clear();
        itemsCount = 0;
    }
}
