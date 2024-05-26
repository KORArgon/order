package com.argon.order.api;

import com.argon.order.domain.FoodMenu;
import com.argon.order.domain.OrderHistory;
import com.argon.order.domain.OrderHistoryMenu;
import com.argon.order.service.OrderHistoryMenuService;
import com.argon.order.service.OrderHistoryService;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2")
public class OrderHistoryApi {

    // orderHistoryService
    private final OrderHistoryService orderHistoryService;

    // orderHistoryMenuService
    private final OrderHistoryMenuService orderHistoryMenuService;

    @GetMapping("/order-history")
    public ResponseEntity<Object> orderHistoryView(String orderId){

        JSONObject jsonObject = new JSONObject();
        Map<String, String> mapHeader = new HashMap<>();

        OrderHistory orderHistory = orderHistoryService.findByOrderId(orderId);
        List<Object> orderHistoryMenuList = orderHistoryService.selectOrderHistoryMenuList(orderId);

        if (orderId == null || orderId.isEmpty()){
            mapHeader.put("result","fail");
            mapHeader.put("message", "no id");
            jsonObject.put("header", mapHeader);
        }
        else if(orderHistory == null){
            mapHeader.put("result","fail");
            mapHeader.put("message", "no order details");
            jsonObject.put("header", mapHeader);
        }
        else {
            mapHeader.put("result","success");
            jsonObject.put("header", mapHeader);

            LinkedHashMap<String, Object> mapBody = new LinkedHashMap<>();

            mapBody.put("orderId",orderHistory.getOrderId());
            mapBody.put("restaurantId",orderHistory.getRestaurantId());
            mapBody.put("tableNo",orderHistory.getTableNo());
            mapBody.put("registDate",orderHistory.getRegistDate());

            JSONObject jsonObjectBody;
            List<Map<String, Object>> mapList = new ArrayList<>();
            Object[] objects;
            OrderHistoryMenu orderHistoryMenu;
            FoodMenu foodMenu;
            for(Object object : orderHistoryMenuList){
                jsonObjectBody = new JSONObject();
                objects = (Object[]) object;
                orderHistoryMenu = (OrderHistoryMenu) objects[0];
                foodMenu = (FoodMenu) objects[1];
                jsonObjectBody.put("foodMenuNo", orderHistoryMenu.getFoodMenuNo());
                jsonObjectBody.put("orderCnt", orderHistoryMenu.getOrderCnt());
                jsonObjectBody.put("foodMenuNm", foodMenu.getFoodMenuNm());
                jsonObjectBody.put("foodMenuPrice", foodMenu.getFoodMenuPrice());
                mapList.add(jsonObjectBody);
            }
            mapBody.put("menu",mapList);
            jsonObject.put("body", mapBody);
        }

        return ResponseEntity.ok(jsonObject);

    }

    @PostMapping("/order-history")
    public ResponseEntity<Object> orderHistoryRegist(OrderHistory orderHistory,int[] foodMenuNo, int[] orderCnt){

        orderHistoryService.save(orderHistory);
        OrderHistoryMenu orderHistoryMenu;
        for(int i=0; i<foodMenuNo.length; i++){
            orderHistoryMenu = OrderHistoryMenu.builder()
                    .orderId(orderHistory.getOrderId())
                    .foodMenuNo(foodMenuNo[i])
                    .orderCnt(orderCnt[i])
                    .build();
            orderHistoryMenuService.save(orderHistoryMenu);
        }

        JSONObject jsonObject = new JSONObject();
        Map<String, String> mapHeader = new HashMap<>();

        mapHeader.put("result","success");
        jsonObject.put("header", mapHeader);
        return ResponseEntity.ok(jsonObject);
    }

    @PutMapping("/order-history")
    public ResponseEntity<Object> orderHistoryUpdate(OrderHistory orderHistory, int[] foodMenuNo, int[] orderCnt){
        orderHistoryService.save(orderHistory);
        OrderHistoryMenu orderHistoryMenu;
        orderHistoryMenuService.deleteByOrderId(orderHistory.getOrderId());
        for(int i=0; i<foodMenuNo.length; i++){
            orderHistoryMenu = OrderHistoryMenu.builder()
                    .orderId(orderHistory.getOrderId())
                    .foodMenuNo(foodMenuNo[i])
                    .orderCnt(orderCnt[i])
                    .build();
            orderHistoryMenuService.save(orderHistoryMenu);
        }

        JSONObject jsonObject = new JSONObject();
        Map<String, String> mapHeader = new HashMap<>();

        mapHeader.put("result","success");
        jsonObject.put("header", mapHeader);
        return ResponseEntity.ok(jsonObject);
    }

    @DeleteMapping("/order-history")
    public ResponseEntity<Object> orderHistoryDelete(String orderId){

        orderHistoryMenuService.deleteByOrderId(orderId);
        orderHistoryService.deleteByOrderId(orderId);

        JSONObject jsonObject = new JSONObject();
        Map<String, String> mapHeader = new HashMap<>();

        mapHeader.put("result","success");
        jsonObject.put("header", mapHeader);

        return ResponseEntity.ok(jsonObject);
    }

}
