package com.back.boundcontext.payout.out;

import com.back.boundcontext.payout.domain.Payout;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PayoutRepository extends JpaRepository<Payout, Long> {
}
