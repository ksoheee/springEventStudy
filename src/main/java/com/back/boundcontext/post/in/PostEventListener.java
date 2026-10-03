package com.back.boundcontext.post.in;

import com.back.boundcontext.post.app.PostFacade;
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
public class PostEventListener {
    private final PostFacade postFacade;

    //기존 트랜잭션이 성공적으로 커밋된 다음 이벤트 처리
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(propagation = Propagation.REQUIRES_NEW)      //새로운 트랜잭션에서
    public void handel(MemberCreatedEvent event){
        postFacade.syncMember(event.getMember());
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void handel(MemberModifiedEvent event){
        postFacade.syncMember(event.getMember());
    }

}
