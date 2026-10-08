package com.back.boundcontext.cash.domain;

import com.back.global.jpa.entity.BaseEntity;
import com.back.global.jpa.entity.BaseManualIdAndTime;
import com.back.shared.cash.dto.WalletDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

import static jakarta.persistence.CascadeType.PERSIST;
import static jakarta.persistence.CascadeType.REMOVE;

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

    private long balance;

    @OneToMany(mappedBy = "wallet",cascade = {PERSIST, REMOVE},orphanRemoval = true)
    private List<CashLog> cashLog;



    public Wallet(CashMember holder){
        super(holder.getId());
        this.holder = holder;
    }

    public WalletDto toDto(){
        return new WalletDto(
                getId(),
                getCreatedDate(),
                getModifiedDate(),
                holder.getId(),
                holder.getUsername(),
                balance
        );
    }

    //잔돈이 남아 있는지
    public boolean isBalance(){
        return balance > 0;
    }
    //입금
    public void credit(long amount, CashLog.EventType eventType, String relTypeCode, Long relId){
        balance +=  amount;
        addCashLog(amount,eventType,relTypeCode,relId);
    }

    public void credit(long amount, CashLog.EventType eventType, BaseEntity rel){
        credit(amount, eventType, rel.getModelType(), rel.getId());
    }

    public void credit(long amount, CashLog.EventType eventType){
        credit(amount, eventType, holder);
    }

    //출금
    public void debit(long amount, CashLog.EventType eventType, String relTypeCode, Long relId){
        balance -= amount;
        addCashLog(-amount, eventType, relTypeCode, relId);
    }

    public void debit(long amount, CashLog.EventType eventType, BaseEntity rel){
        debit(-amount, eventType, rel.getModelType(), rel.getId());
    }
    public void debit(long amount, CashLog.EventType eventType){
        balance -= amount;
        debit(-amount, eventType, holder);
    }

    //로그 추가
    public CashLog addCashLog(long amount, CashLog.EventType eventType, String relTypeCode, Long relId){
        CashLog log = new CashLog(
                eventType,
                relTypeCode,
                relId,
                holder,
                this,
                amount,
                balance
        );
        cashLog.add(log);
        return log;
    }



}
