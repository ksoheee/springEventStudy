package com.back.boundcontext.cash.app;

import com.back.boundcontext.cash.domain.CashMember;
import com.back.boundcontext.cash.domain.Wallet;
import com.back.boundcontext.cash.out.CashMemberRepository;
import com.back.boundcontext.cash.out.WalletRepository;
import com.back.shared.member.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CashFacade {
    private final CashSyncMemberUserCase cashSyncMemberUserCase;
    private final WalletRepository walletRepository;

    @Transactional
    public CashMember syncMember(MemberDto member){
        return cashSyncMemberUserCase.syncMember(member);
    }

    @Transactional
    public Wallet createWallet(CashMember cashMember){
        Wallet wallet = new Wallet(cashMember);
        return walletRepository.save(wallet);
    }

}
