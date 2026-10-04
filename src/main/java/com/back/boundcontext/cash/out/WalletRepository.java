package com.back.boundcontext.cash.out;

import com.back.boundcontext.cash.domain.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<Wallet, Long> {
}
