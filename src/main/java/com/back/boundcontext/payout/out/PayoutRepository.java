package com.back.boundcontext.payout.out;

import com.back.boundcontext.payout.domain.Payout;
import com.back.boundcontext.payout.domain.PayoutMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PayoutRepository extends JpaRepository<Payout, Long> {
    Optional<Payout> findByPayeeAndPayoutDateIsNull(PayoutMember payee);
}
