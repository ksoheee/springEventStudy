package com.back.boundcontext.payout.domain;

import com.back.global.jpa.entity.BaseIdAndTime;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "PAYOUT_PAYOUT_CANDIDATE_ITEM")
@Getter
@NoArgsConstructor
public class PayoutCandidateItem extends BaseIdAndTime {
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

    public PayoutCandidateItem(
            PayoutEventType eventType,
            String relTypeCode,
            Long relId,
            LocalDateTime paymentDate,
            PayoutMember payer,
            PayoutMember payee,
            long amount
            ) {
        this.eventType = eventType;
        this.relTypeCode = relTypeCode;
        this.relId = relId;
        this.paymentDate = paymentDate;
        this.payer = payer;
        this.payee = payee;
        this.amount = amount;
    }

}
