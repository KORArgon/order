package com.argon.order.service;

import com.argon.order.domain.Restaurant;
import com.argon.order.repository.RestaurantRepository;
import com.argon.order.util.DateUtil;
import com.argon.order.util.LoginUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RestaurantService {

    // restaurantRepository
    private final RestaurantRepository restaurantRepository;

    // restaurantService
    public RestaurantService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    /**
     * 전체 조회
     * @param pageable
     * @return
     */
    public Page<Restaurant> findByMemberId(Pageable pageable) {
        return restaurantRepository.findByMemberId(pageable, LoginUtil.getLoingId());
    }

    /**
     * 상세 조회
     * @param id
     * @return
     */
    public Restaurant findByRestaurantId(String id){
        return restaurantRepository.findByRestaurantId(id);
    }

    /**
     * 등록 및 수정 처리
     * @param restaurant
     */
    public void save(Restaurant restaurant) {
        restaurant.setMemberId(LoginUtil.getLoingId());
        if(restaurant.getRestaurantNo() == null){
            StringBuffer sb = new StringBuffer();
            if(LoginUtil.getLoginIsAdmin()) sb.append("A");
            if(!LoginUtil.getLoginIsAdmin()) sb.append("B");
            sb.append(DateUtil.getTodateTime());
            sb.append(String.format("%04d",restaurantRepository.selectRestaurantNo()));
            restaurant.setRestaurantId(sb.toString());
            restaurant.setRegistDate(DateUtil.getTodateTime());
            restaurant.setRegistId(LoginUtil.getLoingId());
        }   else {
            restaurant.setUpdateDate(DateUtil.getTodateTime());
            restaurant.setUpdateId(LoginUtil.getLoingId());
        }

        restaurantRepository.save(restaurant);

    }

    /**
     * 삭제 처리
     * @param restaurant
     */
    public void restaurantDelete(Restaurant restaurant) {
        restaurantRepository.delete(restaurant);
    }

    public Restaurant findByMemberIdAndRestaurantId(String userId, String restaurantId) {
        return restaurantRepository.findByMemberIdAndRestaurantId(userId, restaurantId);
    }
}
