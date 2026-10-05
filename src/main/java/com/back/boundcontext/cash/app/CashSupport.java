package com.back.boundcontext.cash.app;

import com.back.boundcontext.cash.domain.CashMember;
import com.back.boundcontext.cash.domain.Wallet;
import com.back.boundcontext.cash.out.CashMemberRepository;
import com.back.boundcontext.cash.out.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CashSupport {
    private final CashMemberRepository cashMemberRepository;
    private final WalletRepository walletRepository;

    @Transactional
    public Optional<CashMember> findMemberByUsername(String username){
        return cashMemberRepository.findByUsername(username);
    }

    @Transactional
    public Optional<Wallet> findWalletByHolder(CashMember holder){
        return walletRepository.findByHolder(holder);
    }
}
