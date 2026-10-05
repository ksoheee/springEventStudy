package com.back.boundcontext.member.app;

import com.back.boundcontext.member.domain.MemberPolicy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberGetRandomSecureTipUseCase {
    private final MemberPolicy memberPolicy;

    public String getRandomSecureTip(){
        return "비밀번호의 유효기간은 %d입니다.".formatted(memberPolicy.getNeedToChangePasswordDays());
    }
}
