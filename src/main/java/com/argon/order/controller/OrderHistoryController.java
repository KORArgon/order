package com.argon.order.controller;

import com.argon.order.domain.OrderHistory;
import com.argon.order.domain.OrderHistoryMenu;
import com.argon.order.service.MessageService;
import com.argon.order.service.OrderHistoryMenuService;
import com.argon.order.service.OrderHistoryService;
import com.argon.order.util.PagingUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderHistoryController {

    // orderHistoryService
    private final OrderHistoryService orderHistoryService;

    // orderHistoryMenuService
    private final OrderHistoryMenuService orderHistoryMenuService;

    // messageService
    private final MessageService messageService;

    /**
     * 주문내역 목록 조회
     * @param model
     * @return
     */
    @GetMapping("/orderHistoryListForm")
    public String orderHistoryListForm(Model model, @PageableDefault(page=0, size=10, sort="registDate", direction = Sort.Direction.DESC) Pageable pageable, HttpServletRequest request){
        Page<OrderHistory> orderHistoryList = orderHistoryService.findByMemberId(pageable, request);
        model.addAttribute("totCnt",orderHistoryList.getTotalElements());
        model.addAttribute("orderHistoryList", orderHistoryList);
        PagingUtil.getPaginationInfo(model, orderHistoryList);
        return "orderHistory/orderHistoryList";
    }

    /**
     * 주문내역 상세 조회
     * @param orderHistory
     * @param model
     * @return
     */
    @GetMapping("/orderHistoryViewForm")
    public String orderHistoryViewForm(OrderHistory orderHistory, Model model){
        OrderHistory result = orderHistoryService.findByOrderId(orderHistory.getOrderId());
        model.addAttribute("orderHistory", result);
        List<Object> orderHistoryMenuList = orderHistoryService.selectOrderHistoryMenuList(orderHistory.getOrderId());
        model.addAttribute("orderHistoryMenuList", orderHistoryMenuList);
        return "orderHistory/orderHistoryView";
    }

    /**
     * 주문내역 등록 페이지
     * @return
     */
    @GetMapping("/orderHistoryRegistForm")
    public String orderHistoryRegistForm(){
        return "orderHistory/orderHistoryRegist";
    }

    /**
     * 주문내역 등록 처리
     * @param orderHistory
     * @param model
     * @param foodMenuNo
     * @param orderCnt
     * @return
     */
    @PostMapping("/orderHistoryRegist")
    public String orderHistoryRegist(OrderHistory orderHistory, Model model, int[] foodMenuNo, int[] orderCnt){
        orderHistoryService.save(orderHistory);
        OrderHistoryMenu orderHistoryMenu;
        for(int i=0; i<foodMenuNo.length; i++){
            orderHistoryMenu = new OrderHistoryMenu();
            orderHistoryMenu.setOrderId(orderHistory.getOrderId());
            orderHistoryMenu.setFoodMenuNo(foodMenuNo[i]);
            orderHistoryMenu.setOrderCnt(orderCnt[i]);
            orderHistoryMenuService.save(orderHistoryMenu);
        }

        return messageService.redirectMessage(model, "등록을 완료했습니다.", "/orderHistoryListForm");
    }

    /**
     * 주문내역 수정 페이지
     * @param orderHistory
     * @param model
     * @return
     */
    @GetMapping("/orderHistoryUpdateForm")
    public String orderHistoryUpdateForm(OrderHistory orderHistory, Model model){
        OrderHistory result = orderHistoryService.findByOrderId(orderHistory.getOrderId());
        model.addAttribute("orderHistory", result);
        List<Object> orderHistoryMenuList = orderHistoryService.selectOrderHistoryMenuList(orderHistory.getOrderId());
        model.addAttribute("orderHistoryMenuList", orderHistoryMenuList);
        return "orderHistory/orderHistoryUpdate";
    }

    /**
     * 주문내역 수정 처리
     * @param orderHistory
     * @param model
     * @return
     */
    @PutMapping("/orderHistoryUpdate")
    public String orderHistoryUpdate(OrderHistory orderHistory, Model model, int[] foodMenuNo, int[] orderCnt){
        orderHistoryService.save(orderHistory);
        OrderHistoryMenu orderHistoryMenu;
        for(int i=0; i<foodMenuNo.length; i++){
            orderHistoryMenu = new OrderHistoryMenu();
            orderHistoryMenu.setOrderId(orderHistory.getOrderId());
            orderHistoryMenu.setFoodMenuNo(foodMenuNo[i]);
            orderHistoryMenu.setOrderCnt(orderCnt[i]);
            orderHistoryMenuService.save(orderHistoryMenu);
        }
        return messageService.redirectMessage(model, "수정을 완료했습니다.", "/orderHistoryListForm");
    }

    /**
     * 주문내역 삭제 처리
     * @param orderHistory
     * @param model
     * @return
     */
    @DeleteMapping("/orderHistoryDelete")
    public String orderHistoryDelete(OrderHistory orderHistory, Model model){
        orderHistoryService.orderHistoryDelete(orderHistory);
        return messageService.redirectMessage(model, "삭제를 완료했습니다.", "/orderHistoryListForm");
    }
    
}
