package com.argon.order.repository;

import com.argon.order.domain.Restaurant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    Restaurant findByRestaurantId(String restaurantId);

    Page<Restaurant> findByMemberId(Pageable pageable, String memberId);

    @Query(value = "SELECT (RESTAURANT_NO)+1 FROM NT_RESTAURANT ORDER BY RESTAURANT_NO DESC LIMIT 1", nativeQuery = true)
    int selectRestaurantNo();

    Restaurant findByMemberIdAndRestaurantId(String userId, String restaurantId);
}
