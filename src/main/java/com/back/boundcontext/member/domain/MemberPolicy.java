package com.back.boundcontext.member.domain;

import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
public class MemberPolicy {
    private static int PASSWORD_CHANGE_DAYS = 90;

    public int getNeedToChangePasswordDays() {
        return PASSWORD_CHANGE_DAYS;
    }

    //시간 객체로 반환
    public Duration getNeedToChangePasswordPeriod(){
        return Duration.ofDays(PASSWORD_CHANGE_DAYS);
    }

    public int getPasswordChangeDays() {
        return PASSWORD_CHANGE_DAYS;
    }

    public boolean inNeedToChangePassword(LocalDateTime lastChangeDate) {
        if(lastChangeDate == null) return true;
        return lastChangeDate.plusDays(PASSWORD_CHANGE_DAYS).isBefore(LocalDateTime.now());
    }



}
