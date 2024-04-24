package com.argon.order.repository;

import com.argon.order.domain.OrderHistory;
import com.argon.order.domain.OrderHistoryMenu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderHistoryRepository extends JpaRepository<OrderHistory, Long> {
    OrderHistory findByOrderId(String id);

    Page<OrderHistory> findByRestaurantId(Pageable pageable, String restaurantId);

    OrderHistory findByOrderHistoryNo(Long orderHistoryNo);

    @Query("SELECT ohm FROM OrderHistoryMenu OHM WHERE ohm.orderId = :orderId ORDER BY ohm.orderHistoryMenuNo")
    List<OrderHistoryMenu> selectOrderHistoryMenuList(@Param("orderId") String orderId);
}
