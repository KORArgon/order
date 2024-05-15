package com.argon.order.api;

import com.argon.order.domain.OrderHistory;
import com.argon.order.domain.OrderHistoryMenu;
import com.argon.order.service.OrderHistoryMenuService;
import com.argon.order.service.OrderHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2")
public class OrderHistoryApiController {

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
        return ResponseEntity.ok(orderHistoryMenuService);
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
        return ResponseEntity.ok(orderHistoryMenuService);
    }

    @DeleteMapping("/order-history")
    public ResponseEntity<Object> orderHistoryDelete(OrderHistory orderHistory){



        return ResponseEntity.ok(orderHistoryMenuService);
    }

}
