package com.argon.order.api;

import com.argon.order.domain.FoodMenu;
import com.argon.order.domain.Restaurant;
import com.argon.order.service.FoodMenuService;
import com.argon.order.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2")
public class FoodMenuApi {

    // foodMenuService
    private final FoodMenuService foodMenuService;

    // restaurantService
    private final RestaurantService restaurantService;

    @GetMapping("/foodMenuList")
    public ResponseEntity<Object> foodMenuListApi(String id) {

        JSONObject jsonObject = new JSONObject();
        Map<String, String> mapHeader = new HashMap<>();
        List<FoodMenu> list = foodMenuService.findAllByRestaurantId(id);
        Restaurant restaurant = restaurantService.findByRestaurantId(id);
        // header 값 설정
        if(id == null || id == ""){
            mapHeader.put("result","실패");
            mapHeader.put("message", "id값이 없습니다.");
            jsonObject.put("header", mapHeader);
        }
        else if(restaurant == null){
            mapHeader.put("result","실패");
            mapHeader.put("message", "식당이 없습니다.");
            jsonObject.put("header", mapHeader);
        }
        else if (list.size()==0) {
            mapHeader.put("result","실패");
            mapHeader.put("message", "메뉴가 없습니다.");
            jsonObject.put("header", mapHeader);
        }
        else{
            mapHeader.put("result","성공");
            jsonObject.put("header", mapHeader);

            List<Map<String, Object>> mapList = new ArrayList<>();
            Map<String, Object> mapBody;
            JSONObject jsonObjectBody;
            for(FoodMenu foodMenu : list){
                mapBody = new HashMap<>();
                jsonObjectBody  = new JSONObject();

                mapBody.put("foodMenuNo",foodMenu.getFoodMenuNo());
                mapBody.put("restaurantId", foodMenu.getRestaurantId());
                mapBody.put("foodMenuNm", foodMenu.getFoodMenuNm());
                mapBody.put("foodMenuPrice", foodMenu.getFoodMenuPrice());
                mapBody.put("menuCategory", foodMenu.getMenuCategory());
                mapBody.put("popularAt", foodMenu.getPopularAt());
                mapBody.put("foodMenuTitle", foodMenu.getFoodMenuTitle());

                jsonObjectBody.put("name", foodMenu.getFoodImgName());
                jsonObjectBody.put("storeName", foodMenu.getFoodImgStoreName());
                jsonObjectBody.put("path", foodMenu.getFoodImgPath());
                mapBody.put("image", jsonObjectBody);

                mapList.add(mapBody);
            }

            jsonObject.put("body", mapList);
        }


        return ResponseEntity.ok(jsonObject);
    }
}
