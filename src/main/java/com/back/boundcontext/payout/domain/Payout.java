package com.back.boundcontext.payout.domain;

import com.back.global.jpa.entity.BaseIdAndTime;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Table(name = "PAYOUT_PAYOUT")
@Getter
@NoArgsConstructor
public class Payout extends BaseIdAndTime {
    @ManyToOne(fetch = LAZY)
    private PayoutMember payee;
    @Setter
    private LocalDateTime payoutDate;
    private long amount;

    @OneToMany(mappedBy = "payout",cascade = {CascadeType.REMOVE,CascadeType.PERSIST},orphanRemoval = true)
    private List<PayoutItem> items = new ArrayList<>();

    public Payout(PayoutMember payee) {
        this.payee = payee;
    }

    public PayoutItem addItem(PayoutEventType eventType, String relTypeCode, Long relId, LocalDateTime paymentDate, PayoutMember payer, PayoutMember payee, long amount){
        PayoutItem item = new PayoutItem(
                this, eventType, relTypeCode, relId, paymentDate, payer, payee, amount
        );
        items.add(item);
        this.amount+=amount;
        return item;

    }

}
