package com.back.shared.market.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class MarketMemberDto {
    private final Long id;
    private final LocalDateTime createdDate;
    private final LocalDateTime modifiedDate;
    private final String username;
    private final String nickname;
    private final int activityScore;
}
