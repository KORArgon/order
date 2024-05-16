package com.argon.order.api;

import com.argon.order.domain.OrderHistory;
import com.argon.order.domain.OrderHistoryMenu;
import com.argon.order.service.OrderHistoryMenuService;
import com.argon.order.service.OrderHistoryService;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            mapHeader.put("result","실패");
            mapHeader.put("message", "id값이 없습니다.");
            jsonObject.put("header", mapHeader);
        }
        else if(orderHistory == null){
            mapHeader.put("result","실패");
            mapHeader.put("message", "주문내역이 없습니다.");
            jsonObject.put("header", mapHeader);
        }
        else {
            mapHeader.put("result","성공");
            jsonObject.put("header", mapHeader);

            Map<String, Object> mapBody = new HashMap<>();

            mapBody.put("orderId",orderHistory.getOrderId());
            mapBody.put("restaurantId",orderHistory.getRestaurantId());
            mapBody.put("tableNo",orderHistory.getTableNo());
            mapBody.put("registDate",orderHistory.getRegistDate());

            JSONObject jsonObjectBody;

            for(Object orderHistoryMenu : orderHistoryMenuList){

            }

            jsonObject.put("body", mapBody);
        }

        return ResponseEntity.ok(jsonObject);

    }

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
        orderHistoryMenuService.deleteByOrderId(orderHistory.getOrderId());
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
