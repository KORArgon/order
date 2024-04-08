package com.argon.order.repository;

import com.argon.order.domain.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {


    List<Restaurant> findByRestaurantId(String restaurantId);
}
