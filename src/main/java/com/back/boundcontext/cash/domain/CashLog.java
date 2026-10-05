package com.back.boundcontext.cash.domain;

import com.back.global.jpa.entity.BaseIdAndTime;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "CASH_CASH_LOG")
@NoArgsConstructor
public class CashLog extends BaseIdAndTime {
    public enum EventType {
        충전__무통장입금,
        충전__PG결제_토스페이먼츠,
        출금_통장입금,
        사용_주문결제,
        임시보관__주문결제,
        정산지급__상품판매_수수료,
        정산수령__상품판매_수수료,
        정산지금__상품판매_대금,
        정산수령__상품판매_대금
    }

    @Enumerated(EnumType.STRING)
    private EventType eventType;
    //rel: 돈의 이동을 발생시킨 원인이 되는 객체
    private String relTypeCode;
    private Long relId;

    @ManyToOne(fetch = FetchType.LAZY)
    private CashMember holder;
    @ManyToOne(fetch = FetchType.LAZY)
    private Wallet wallet;

    private long amount;
    private long balance;

    public CashLog(EventType eventType, String relTypeCode, Long relId, CashMember holder, Wallet wallet, long amount, long balance) {
        this.eventType = eventType;
        this.relTypeCode = relTypeCode;
        this.relId = relId;
        this.holder = holder;
        this.wallet = wallet;
        this.amount = amount;
        this.balance = balance;
    }
}
