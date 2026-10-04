package com.back.boundcontext.cash.out;

import com.back.boundcontext.cash.domain.CashMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CashMemberRepository extends JpaRepository<CashMember, Long> {
}
