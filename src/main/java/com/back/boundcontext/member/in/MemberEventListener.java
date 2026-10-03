package com.back.boundcontext.member.in;

import com.back.boundcontext.member.app.MemberFacade;
import com.back.boundcontext.member.domain.Member;
import com.back.shared.post.event.PostWriteEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class MemberEventListener {
    private final MemberFacade memberFacade;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void handel(PostWriteEvent event){
        Member member = memberFacade.findById(event.getPostDto().getAuthorId()).get();
        member.increaseActivityScore(3);
    }
}
