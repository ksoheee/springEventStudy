package com.back.boundcontext.member.domain;

import org.springframework.stereotype.Service;

@Service
public class MemberPolicy {
    private static int PASSWORD_CHANGE_DAYS = 90;

    public int getNeedToChangePasswordDays() {
        return PASSWORD_CHANGE_DAYS;
    }



}
