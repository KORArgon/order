package com.argon.order.service;

import com.argon.order.domain.Restaurant;
import com.argon.order.repository.RestaurantRepository;
import com.argon.order.util.DateUtil;
import com.argon.order.util.LoginUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class RestaurantService {

    // restaurantService
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
    public Page<Restaurant> findAll(Pageable pageable) {
        return restaurantRepository.findAll(pageable);
    }

    /**
     * 상세 조회
     * @param id
     * @return
     */
    public Restaurant findById(String id){
        return (Restaurant) restaurantRepository.findByRestaurantId(id);
    }

    /**
     * 등록 및 수정 처리
     * @param restaurant
     */
    public void save(Restaurant restaurant) {
        restaurant.setMemberId(LoginUtil.getLoingId());
        if(restaurantRepository.findByRestaurantId(restaurant.getRestaurantId()).size() == 0){
            String restaurantId = DateUtil.getTodateTime();
            restaurant.setRestaurantId(restaurantId);
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

    public List<Restaurant> findByRestaurantId(String restaurantId) {
        return restaurantRepository.findByRestaurantId(restaurantId);
    }
    
}
