package com.back.boundcontext.payout.domain;

import com.back.global.jpa.entity.BaseIdAndTime;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "PAYOUT_PAYOUT_ITEM")
@NoArgsConstructor
public class PayoutItem extends BaseIdAndTime {
    //어떤 정산에 대한 건지
    //정산 타입이뭔지
    //정산이 되는 객체가 뭔지
    //시각이 언제인지
    //누가 정산대상인지
    //누가 지급해주는 사람인지
    //금액은 얼마인지
    @ManyToOne(fetch = FetchType.LAZY)
    private Payout payout;
    @Enumerated(EnumType.STRING)
    private PayoutEventType eventType;
    private String relTypeCode;
    private Long relId;
    private LocalDateTime paymentDate;
    @ManyToOne(fetch = FetchType.LAZY)
    private PayoutMember payer;
    @ManyToOne(fetch = FetchType.LAZY)
    private PayoutMember payee;
    private long amount;

    public PayoutItem(Payout payout, PayoutEventType eventType, String relTypeCode, Long relId, LocalDateTime paymentDate, PayoutMember payer, PayoutMember payee, long amount) {
        this.payout = payout;
        this.eventType = eventType;
        this.relTypeCode = relTypeCode;
        this.relId = relId;
        this.paymentDate = paymentDate;
        this.payer = payer;
        this.payee = payee;
        this.amount = amount;
    }
}
