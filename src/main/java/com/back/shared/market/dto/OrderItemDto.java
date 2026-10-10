package com.back.shared.market.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class OrderItemDto {
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
}
