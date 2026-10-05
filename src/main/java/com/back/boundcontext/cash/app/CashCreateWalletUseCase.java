package com.back.boundcontext.cash.app;

import com.back.boundcontext.cash.domain.CashMember;
import com.back.boundcontext.cash.domain.Wallet;
import com.back.boundcontext.cash.out.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CashCreateWalletUseCase {
    private final WalletRepository walletRepository;

    public Wallet createWallet(CashMember cashMember){
        Wallet wallet = new Wallet(cashMember);
        return walletRepository.save(wallet);
    }
}
