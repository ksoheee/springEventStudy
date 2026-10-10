package com.back.shared.market.dto;


import com.back.standard.modeltype.HasModelTypeCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class OrderItemDto implements HasModelTypeCode {
    private final Long id;
    private final LocalDateTime createdDate;
    private final LocalDateTime modifiedDate;
    private final Long orderId;
    private final Long buyerId;
    private final String buyerName;
    private final Long sellerId;
    private final String sellerName;
    private final Long productId;
    private final String productName;
    private final long price;
    private final long salePrice;
    private final double payoutRate;
    private final long payoutFee;
    private final long salePriceWithoutFee;

    @Override
    public String getModelTypeCode() {
        return "OrderItem";
    }

}
