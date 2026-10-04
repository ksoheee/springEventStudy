package com.back.boundcontext.cash.in;

import com.back.boundcontext.cash.app.CashFacade;
import com.back.boundcontext.cash.domain.CashMember;
import com.back.shared.member.event.MemberCreatedEvent;
import com.back.shared.member.event.MemberModifiedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class CashEventListener {
    private final CashFacade cashFacade;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void handle(MemberCreatedEvent event){
        CashMember member = cashFacade.syncMember(event.getMember());
        cashFacade.createWallet(member);
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void handle(MemberModifiedEvent event){
        cashFacade.syncMember(event.getMember());
    }

}
