package com.argon.order.repository;

import com.argon.order.domain.OrderHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderHistoryRepository extends JpaRepository<OrderHistory, Long> {
    OrderHistory findByOrderId(String id);

    Page<OrderHistory> findByRestaurantId(Pageable pageable, String restaurantId);

    OrderHistory findByOrderHistoryNo(Long orderHistoryNo);
}
