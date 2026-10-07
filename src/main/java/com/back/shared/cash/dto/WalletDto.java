package com.back.shared.cash.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class WalletDto {
    private final Long id;
    private final LocalDateTime createdDate;
    private final LocalDateTime modifiedDate;
    private final Long holderId;
    private final String holderName;
    private final long balance;
}
