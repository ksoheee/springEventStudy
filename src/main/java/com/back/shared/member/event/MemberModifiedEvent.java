package com.back.shared.member.event;

import com.back.shared.member.dto.MemberDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class MemberModifiedEvent {
    private final MemberDto member;
}
