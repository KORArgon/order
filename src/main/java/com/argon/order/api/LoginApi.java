package com.argon.order.api;

import com.argon.order.domain.Restaurant;
import com.argon.order.service.MemberService;
import com.argon.order.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2")
public class LoginApi {

    // memberService
    private final MemberService memberService;

    // restaurantService
    private final RestaurantService restaurantService;

    @PostMapping("/loginApi")
    public ResponseEntity<Object> loginApi(String restaurantId, int tableNo, String memberId, String password) {

        JSONObject jsonObject = new JSONObject();
        Map<String, String> mapHeader = new HashMap<>();
        Map<String, Object> mapBody = new HashMap<>();

        boolean memberCheck = memberService.memberCheck(memberId, password);
        Restaurant restaurant = restaurantService.findByMemberIdAndRestaurantId(memberId, restaurantId);


        if(restaurantId.isEmpty() || memberId.isEmpty() || password.isEmpty()){
            mapHeader.put("result","실패");
            mapHeader.put("message", "파라미터값을 확인해주세요.");
            jsonObject.put("header", mapHeader);
        }
        else if (!memberCheck) {
            mapHeader.put("result","실패");
            mapHeader.put("message", "사용자ID 또는 패스워드를 확인해주세요.");
            jsonObject.put("header", mapHeader);
        }
        else if(restaurant == null){
            mapHeader.put("result","실패");
            mapHeader.put("message", "사용자ID 또는 식당ID를 확인해주세요.");
            jsonObject.put("header", mapHeader);
        }
        else{
            mapHeader.put("result","성공");
            jsonObject.put("header", mapHeader);

            mapBody.put("tableNo", tableNo);
            mapBody.put("memberId", memberId);
            mapBody.put("restaurantId", restaurantId);
            jsonObject.put("body", mapBody);
        }

        return ResponseEntity.ok(jsonObject);
    }

}
