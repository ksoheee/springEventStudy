package com.back.boundcontext.cash.in;

import com.back.boundcontext.cash.app.CashFacade;
import com.back.shared.cash.dto.WalletDto;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/cash/wallets")
@RequiredArgsConstructor
public class ApiV1WalletController {
    private final CashFacade cashFacade;

    @GetMapping("/by-holder/{holderId}")
    @Transactional(readOnly=true)
    public WalletDto getWalletByHolderId(@PathVariable Long holderId){
        return cashFacade.findWalletByHolderId(holderId)
                .map(wallet -> new WalletDto(
                        wallet.getId(),
                        wallet.getCreatedDate(),
                        wallet.getModifiedDate(),
                        wallet.getHolder().getId(),
                        wallet.getHolder().getUsername(),
                        wallet.getBalance()
                ))
                .get();
    }
}
