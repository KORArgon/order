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

    @Query("SELECT ohm, fm FROM OrderHistoryMenu ohm " +
            "LEFT JOIN FoodMenu fm ON ohm.foodMenuNo = fm.foodMenuNo " +
            "WHERE ohm.orderId = :orderId ORDER BY ohm.orderHistoryMenuNo")
    List<Object> selectOrderHistoryMenuList(@Param("orderId") String orderId);

    void deleteByOrderId(String orderId);
}
