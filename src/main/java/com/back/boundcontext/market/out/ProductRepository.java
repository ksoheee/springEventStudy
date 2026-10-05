package com.back.boundcontext.market.out;

import com.back.boundcontext.market.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
