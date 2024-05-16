package com.argon.order.service;

import com.argon.order.domain.OrderHistory;
import com.argon.order.domain.OrderHistoryMenu;
import com.argon.order.repository.OrderHistoryRepository;
import com.argon.order.util.DateUtil;
import com.argon.order.util.LoginUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class OrderHistoryService {

    // orderHistoryService
    private final OrderHistoryRepository orderHistoryRepository;

    // orderHistoryService
    public OrderHistoryService(OrderHistoryRepository orderHistoryRepository) {
        this.orderHistoryRepository = orderHistoryRepository;
    }

    /**
     * 전체 조회
     * @param pageable
     * @return
     */
    public Page<OrderHistory> findByMemberId(Pageable pageable, HttpServletRequest request) {
        return orderHistoryRepository.findByRestaurantId(pageable, LoginUtil.getRestaurantId(request));
    }

    /**
     * 상세 조회
     * @param id
     * @return
     */
    public OrderHistory findByOrderId(String id){
        return orderHistoryRepository.findByOrderId(id);
    }

    /**
     * 등록 및 수정 처리
     * @param orderHistory
     */
    public void save(OrderHistory orderHistory) {
        if(orderHistoryRepository.findByOrderId(orderHistory.getOrderId()) == null){
            orderHistory.setRegistDate(DateUtil.getTodateTime());
            orderHistory.setRegistId(LoginUtil.getLoingId());
        }   else {
            orderHistory.setUpdateDate(DateUtil.getTodateTime());
            orderHistory.setUpdateId(LoginUtil.getLoingId());
        }

        orderHistoryRepository.save(orderHistory);

    }

    /**
     * 삭제 처리
     * @param orderId
     */
    @Transactional
    public void deleteByOrderId(String orderId) {
        orderHistoryRepository.deleteByOrderId(orderId);
    }

    public List<Object> selectOrderHistoryMenuList(String orderId) {
        return orderHistoryRepository.selectOrderHistoryMenuList(orderId);
    }
}
