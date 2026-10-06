package com.back.boundcontext.market.out;

import com.back.boundcontext.market.domain.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, Long> {
}
