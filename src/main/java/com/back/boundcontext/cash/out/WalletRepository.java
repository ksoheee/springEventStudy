package com.back.boundcontext.cash.out;

import com.back.boundcontext.cash.domain.CashMember;
import com.back.boundcontext.cash.domain.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WalletRepository extends JpaRepository<Wallet, Long> {
    Optional<Wallet> findByHolder(CashMember holder);
    Optional<Wallet> findByHolderId(Long holderId);
}
