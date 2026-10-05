package com.back.boundcontext.cash.app;

import com.back.boundcontext.cash.domain.CashMember;
import com.back.boundcontext.cash.domain.Wallet;
import com.back.boundcontext.cash.out.CashMemberRepository;
import com.back.boundcontext.cash.out.WalletRepository;
import com.back.shared.cash.dto.CashMemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CashCreateWalletUseCase {
    private final WalletRepository walletRepository;
    private final CashMemberRepository cashMemberRepository;

    public Wallet createWallet(CashMemberDto cashMember){
        //DB조회가 아닌 id만 가진 참조를 만듬
        CashMember _member = cashMemberRepository.getReferenceById(cashMember.getId());
        Wallet wallet = new Wallet(_member);
        return walletRepository.save(wallet);
    }
}
