package com.argon.order.api;

import com.argon.order.domain.OrderHistory;
import com.argon.order.domain.OrderHistoryMenu;
import com.argon.order.service.OrderHistoryMenuService;
import com.argon.order.service.OrderHistoryService;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2")
public class OrderHistoryApi {

    // orderHistoryService
    private final OrderHistoryService orderHistoryService;

    // orderHistoryMenuService
    private final OrderHistoryMenuService orderHistoryMenuService;

    @PostMapping("/order-history")
    public ResponseEntity<Object> orderHistoryRegist(OrderHistory orderHistory,int[] foodMenuNo, int[] orderCnt){
        orderHistoryService.save(orderHistory);
        OrderHistoryMenu orderHistoryMenu;
        for(int i=0; i<foodMenuNo.length; i++){
            orderHistoryMenu = new OrderHistoryMenu();
            orderHistoryMenu.setOrderId(orderHistory.getOrderId());
            orderHistoryMenu.setFoodMenuNo(foodMenuNo[i]);
            orderHistoryMenu.setOrderCnt(orderCnt[i]);
            orderHistoryMenuService.save(orderHistoryMenu);
        }

        JSONObject jsonObject = new JSONObject();
        Map<String, String> mapHeader = new HashMap<>();

        mapHeader.put("result","성공");
        jsonObject.put("header", mapHeader);
        return ResponseEntity.ok(jsonObject);
    }

    @PutMapping("/order-history")
    public ResponseEntity<Object> orderHistoryUpdate(OrderHistory orderHistory, int[] foodMenuNo, int[] orderCnt){
        orderHistoryService.save(orderHistory);
        OrderHistoryMenu orderHistoryMenu;
        for(int i=0; i<foodMenuNo.length; i++){
            orderHistoryMenu = new OrderHistoryMenu();
            orderHistoryMenu.setOrderId(orderHistory.getOrderId());
            orderHistoryMenu.setFoodMenuNo(foodMenuNo[i]);
            orderHistoryMenu.setOrderCnt(orderCnt[i]);
            orderHistoryMenuService.save(orderHistoryMenu);
        }

        JSONObject jsonObject = new JSONObject();
        Map<String, String> mapHeader = new HashMap<>();

        mapHeader.put("result","성공");
        jsonObject.put("header", mapHeader);
        return ResponseEntity.ok(jsonObject);
    }

    @DeleteMapping("/order-history")
    public ResponseEntity<Object> orderHistoryDelete(String orderId){

        orderHistoryMenuService.deleteByOrderId(orderId);
        orderHistoryService.deleteByOrderId(orderId);

        JSONObject jsonObject = new JSONObject();
        Map<String, String> mapHeader = new HashMap<>();

        mapHeader.put("result","성공");
        jsonObject.put("header", mapHeader);

        return ResponseEntity.ok(jsonObject);
    }

}
