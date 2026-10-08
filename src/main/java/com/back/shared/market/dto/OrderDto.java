package com.back.shared.market.dto;

import com.back.standard.modeltype.HasModelTypeCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class OrderDto implements HasModelTypeCode {
    private final Long id;
    private final LocalDateTime createdDate;
    private final LocalDateTime modifiedDate;

    private final Long customerId;
    private final String customerName;
    private final long price;
    private final long salePrice;

    private final LocalDateTime requestedPaymentDate;
    private final LocalDateTime paymentDate;

    @Override
    public String getModelTypeCode() {
        return "Order";
    }
}
