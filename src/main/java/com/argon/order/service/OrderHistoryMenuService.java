package com.argon.order.service;

import com.argon.order.domain.OrderHistory;
import com.argon.order.domain.OrderHistoryMenu;
import com.argon.order.repository.OrderHistoryMenuRepository;
import com.argon.order.repository.OrderHistoryRepository;
import com.argon.order.util.DateUtil;
import com.argon.order.util.LoginUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderHistoryMenuService {

    // orderHistoryService
    private final OrderHistoryMenuRepository orderHistoryMenuRepository;

    /**
     * 전체 조회
     * @param pageable
     * @return
     */
    public Page<OrderHistoryMenu> findByOrderId(Pageable pageable, HttpServletRequest request) {
        return orderHistoryMenuRepository.findByOrderId(pageable, LoginUtil.getRestaurantId(request));
    }

    /**
     * 상세 조회
     * @param id
     * @return
     */
    public List<OrderHistoryMenu> findByOrderId(String id){
        return orderHistoryMenuRepository.findByOrderId(id);
    }

    /**
     * 등록 및 수정 처리
     * @param orderHistorymenu
     */
    public void save(OrderHistoryMenu orderHistorymenu) {
        orderHistoryMenuRepository.save(orderHistorymenu);
    }

    /**
     * 삭제 처리
     * @param orderId
     */
    @Transactional
    public void deleteByOrderId(String orderId) {
        orderHistoryMenuRepository.deleteByOrderId(orderId);
    }
    
}
