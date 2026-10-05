package com.back.boundcontext.cash.app;

import com.back.boundcontext.cash.domain.CashMember;
import com.back.boundcontext.cash.domain.Wallet;
import com.back.shared.cash.dto.CashMemberDto;
import com.back.shared.member.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CashFacade {
    private final CashSyncMemberUserCase cashSyncMemberUserCase;
    private final CashCreateWalletUseCase cashCreateWalletUseCase;
    private final CashSupport cashSupport;

    @Transactional
    public CashMember syncMember(MemberDto member){
        return cashSyncMemberUserCase.syncMember(member);
    }

    @Transactional
    public Wallet createWallet(CashMemberDto cashMember){
        return cashCreateWalletUseCase.createWallet(cashMember);
    }

    @Transactional
    public Optional<CashMember> findMemberByUsername(String username){
        return cashSupport.findMemberByUsername(username);
    }

    @Transactional
    public Optional<Wallet> findWalletByHolder(CashMember holder){
        return cashSupport.findWalletByHolder(holder);
    }

}
