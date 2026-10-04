package com.back.boundcontext.cash.domain;

import com.back.global.jpa.entity.BaseManualIdAndTime;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name= "CASH_WALLET")
@NoArgsConstructor
@Getter
public class Wallet extends BaseManualIdAndTime {

    /**
     * Wallet정보만 필요한 경우 CashMember 추가로 조회하지 않도록 하기 위해
     */
    @ManyToOne(fetch= FetchType.LAZY)
    private CashMember holder;

    public Wallet(CashMember holder){
        super(holder.getId());
        this.holder = holder;
    }

}
