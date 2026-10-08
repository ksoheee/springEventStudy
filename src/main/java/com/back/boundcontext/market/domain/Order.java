package com.back.boundcontext.market.domain;

import com.back.global.jpa.entity.BaseIdAndTime;
import com.back.shared.market.dto.OrderDto;
import com.back.shared.market.event.MarketOrderPaymentRequestedEvent;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "MARKET_ORDER")
@Getter
@NoArgsConstructor
public class Order extends BaseIdAndTime {
    @ManyToOne(fetch = FetchType.LAZY)
    private MarketMember buyer;
    @OneToMany(mappedBy = "order", cascade = {CascadeType.REMOVE, CascadeType.PERSIST}, orphanRemoval = true)
    List<OrderItem> items = new ArrayList<>();
    private long price;
    private long salePrice;

    private LocalDateTime requestPaymentDate; //결제 요청 시각
    private LocalDateTime paymentDate;        //결제 완료 시각
    private LocalDateTime canceledDate;


    public Order(Cart cart) {
        this.buyer = cart.getBuyer();
        //장바구니에 있는 상품을 orderitem으로 옮기기
        cart.getItems().forEach(item -> {
            addItem(item.getProduct());
        });
    }

    public Order(MarketMember buyer, Product product) {
        this.buyer = buyer;
        addItem(product);
    }

    public OrderDto toDto() {
        return new OrderDto(
                getId(),
                getCreatedDate(),
                getModifiedDate(),
                buyer.getId(),
                buyer.getNickname(),
                price,
                salePrice,
                requestPaymentDate,
                paymentDate
        );
    }

    public boolean isPaid(){
        return paymentDate != null;
    }

    public boolean isCanceled(){
        return canceledDate != null;
    }

    public boolean isPaymentInProgress(){
        return requestPaymentDate != null && paymentDate == null && canceledDate == null;
    }

    public void completePayment(){
        paymentDate = LocalDateTime.now();
    }

    public void cancelRequestPayment(){
        requestPaymentDate = null;
    }

    public void requestPayment(long pgPaymentAmount){
        requestPaymentDate = LocalDateTime.now();

        publishEvent(new MarketOrderPaymentRequestedEvent(toDto(),pgPaymentAmount));
    }

    public void addItem(Product product){
        OrderItem orderItem = new OrderItem(
                this,
                product,
                product.getName(),
                product.getPrice(),
                product.getSalePrice()
        );
        items.add(orderItem);

        price += product.getPrice();
        salePrice += product.getSalePrice();
    }
}
