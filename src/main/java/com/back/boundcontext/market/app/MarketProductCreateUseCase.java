package com.back.boundcontext.market.app;

import com.back.boundcontext.market.domain.MarketMember;
import com.back.boundcontext.market.domain.Product;
import com.back.boundcontext.market.out.ProductRepository;
import com.back.shared.post.out.PostApiClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MarketProductCreateUseCase {
    private final ProductRepository productRepository;
    private final PostApiClient postApiClient;

    public Product createProduct(MarketMember seller, String sourceType, Long sourceId, String name, String description, long price, long salePrcie){
        Product product = new Product(
            seller,sourceType,sourceId,name,description,price,salePrcie
        );
        return productRepository.save(product);
    }
}
