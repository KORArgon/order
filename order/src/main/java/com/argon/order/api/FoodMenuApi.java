package com.argon.order.api;

import com.argon.order.domain.FoodMenu;
import com.argon.order.service.FoodMenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FoodMenuApi {

    // foodMenuService
    private final FoodMenuService foodMenuService;

    @GetMapping("/foodMenu/foodMenuListApi")
    public ResponseEntity<Object> foodMenuListApi(String restaurantId) {
        return ResponseEntity.ok(foodMenuService.findByRestaurantId(restaurantId));
    }
}
