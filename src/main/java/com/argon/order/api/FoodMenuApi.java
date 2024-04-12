package com.argon.order.api;

import com.argon.order.service.FoodMenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FoodMenuApi {

    // foodMenuService
    private final FoodMenuService foodMenuService;

    @GetMapping("/foodMenuListApi")
    public ResponseEntity<Object> foodMenuListApi(Pageable pageable, String restaurantId) {
        return ResponseEntity.ok(foodMenuService.findByRestaurantId(pageable, restaurantId));
    }
}
