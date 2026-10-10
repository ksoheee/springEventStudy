package com.back.boundcontext.payout.out;

import com.back.boundcontext.payout.domain.PayoutCandidateItem;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface PayoutCandidateItemRepository extends JpaRepository<PayoutCandidateItem, Long> {
    //아직 PayoutItem에 연결되지 않은 정산 후보 중, 기준 결제일 이전의 항목을 조회한다.
    List<PayoutCandidateItem> findByPayoutItemIsNullAndPaymentDateBeforeOrderByPayeeAscIdAsc(LocalDateTime paymentDate, Pageable pageable);
}
