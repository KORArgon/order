package com.argon.order.controller;

import com.argon.order.domain.FoodMenu;
import com.argon.order.service.FoodMenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FoodMenuApiController {

    // foodMenuService
    private final FoodMenuService foodMenuService;

    @GetMapping("/foodMenu/foodMenuListApi")
    public List<FoodMenu> foodMenuListApi(String restaurantId){
        return foodMenuService.findByRestaurantId(restaurantId);
    }

}
