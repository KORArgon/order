package com.argon.order.repository;

import com.argon.order.domain.FoodMenu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FoodMenuRepository extends JpaRepository<FoodMenu, Long> {

    Page<FoodMenu> findByRestaurantId(Pageable pageable, String restaurantId);
}
