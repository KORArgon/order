package com.argon.order.service;

import com.argon.order.domain.OrderHistory;
import com.argon.order.repository.OrderHistoryRepository;
import com.argon.order.util.DateUtil;
import com.argon.order.util.LoginUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
        /*if(orderHistoryRepository.findByOrderHistoryId(orderHistory.getOrderHistoryId()) == null){
            StringBuffer sb = new StringBuffer();
            if(LoginUtil.getLoginIsAdmin()) sb.append("A");
            if(!LoginUtil.getLoginIsAdmin()) sb.append("B");
            sb.append(DateUtil.getTodateTime());
            sb.append(String.format("%04d",orderHistoryRepository.selectOrderHistoryNo()));
            orderHistory.setOrderHistoryId(sb.toString());
            orderHistory.setRegistDate(DateUtil.getTodateTime());
            orderHistory.setRegistId(LoginUtil.getLoingId());
        }   else {
            orderHistory.setUpdateDate(DateUtil.getTodateTime());
            orderHistory.setUpdateId(LoginUtil.getLoingId());
        }*/

        orderHistoryRepository.save(orderHistory);

    }

    /**
     * 삭제 처리
     * @param orderHistory
     */
    public void orderHistoryDelete(OrderHistory orderHistory) {
        orderHistoryRepository.delete(orderHistory);
    }
    
}
