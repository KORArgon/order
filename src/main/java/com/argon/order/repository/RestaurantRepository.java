package com.argon.order.repository;

import com.argon.order.domain.Restaurant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    Restaurant findByRestaurantId(String restaurantId);

    Page<Restaurant> findByMemberId(Pageable pageable, String memberId);
}
