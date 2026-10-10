package com.back.boundcontext.payout.app;

import com.back.boundcontext.payout.domain.*;
import com.back.boundcontext.payout.out.PayoutCandidateItemRepository;
import com.back.boundcontext.payout.out.PayoutRepository;
import com.back.global.rsData.RsData;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PayoutCollectPayoutItemsMoreUseCase {
    private final PayoutRepository payoutRepository;
    private final PayoutCandidateItemRepository payoutCandidateItemRepository;

    public RsData<Integer> collectPayoutItemsMore(int limit){
        // 아직 정산 항목에 연결되지 않았고, 정산 대기 기간이 지난 후보를 최대 limit건 조회
        List<PayoutCandidateItem> payoutReadyCandidateItems = findPayoutCandidateItems(limit);

        //Empty라면 return
        if(payoutReadyCandidateItems.isEmpty()){
            return new RsData<>("200-1","더이상 정산에 추가할 항목이 없습니다.",0);
        }

        payoutReadyCandidateItems
                .stream()
                .collect(Collectors.groupingBy(payoutCandidateItem -> payoutCandidateItem.getPayee()))//수취인 별로 그룹화
                .forEach((payee, candidateItems) -> {
                    Payout payout = findActiveByPayee(payee).get(); //수취인의 payout가져옴

                    //정산후보를 루프돌면서 payoutItem에 저장
                    candidateItems.forEach(item -> {
                        PayoutItem payoutItem = payout.addItem(
                                item.getEventType(),
                                item.getRelTypeCode(),
                                item.getRelId(),
                                item.getPaymentDate(),
                                item.getPayer(),
                                item.getPayee(),
                                item.getAmount()
                        );
                        item.setPayoutItem(payoutItem); //candidateItems와 payoutItem연결
                    });
                });
        return new RsData<>(
                "201-1",("" +
                "%d건의 정산 데이터가 생성되었습니다.").formatted(payoutReadyCandidateItems.size()),
                payoutReadyCandidateItems.size()
                );
    }

    private List<PayoutCandidateItem> findPayoutCandidateItems(int limit){
        //현재 날짜와 시간에서 정산 대기 일수를 뺀 뒤, > 시간 정보 제거하고 날짜만 남긴뒤 > 해당날짜의 시간을 00:00:00으로 설정
        LocalDateTime daysAgo = LocalDateTime.now()
                .minusDays(PayoutPolicy.PAYOUT_READY_WAITING_DAYS)
                .toLocalDate()
                .atStartOfDay();
        return payoutCandidateItemRepository.findByPayoutItemIsNullAndPaymentDateBeforeOrderByPayeeAscIdAsc(daysAgo, PageRequest.of(0, limit));
    }

    private Optional<Payout> findActiveByPayee(PayoutMember payee){
        return payoutRepository.findByPayeeAndPayoutDateIsNull(payee);
    }
}
