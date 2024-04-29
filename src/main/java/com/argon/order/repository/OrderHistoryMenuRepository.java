package com.argon.order.repository;

import com.argon.order.domain.OrderHistoryMenu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderHistoryMenuRepository extends JpaRepository<OrderHistoryMenu, Long> {
    OrderHistoryMenu findByOrderId(String id);

    Page<OrderHistoryMenu> findByOrderId(Pageable pageable, String restaurantId);

    OrderHistoryMenu findByOrderHistoryMenuNo(Long orderHistoryMenuNo);

    void deleteByOrderId(String orderId);
}
