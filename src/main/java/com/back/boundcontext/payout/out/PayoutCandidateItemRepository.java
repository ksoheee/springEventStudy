package com.back.boundcontext.payout.out;

import com.back.boundcontext.payout.domain.PayoutCandidateItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PayoutCandidateItemRepository extends JpaRepository<PayoutCandidateItem, Long> {
}
