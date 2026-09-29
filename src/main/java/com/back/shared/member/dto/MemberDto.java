package com.back.shared.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class MemberDto {
    private final Long id;
    private final LocalDateTime createdDate;
    private final LocalDateTime modifiedDate;
    private final String username;
    private final String password;
    private final String nickname;
    private final int activityScore;
}
