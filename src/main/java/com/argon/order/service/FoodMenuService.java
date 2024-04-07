package com.argon.order.service;

import com.argon.order.domain.FoodMenu;
import com.argon.order.repository.FoodMenuRepository;
import com.argon.order.util.DateUtil;
import com.argon.order.util.LoginUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.List;

@Service
@Slf4j
public class FoodMenuService {

    // foodMenuService
    private final FoodMenuRepository foodMenuRepository;

    // foodMenuService
    public FoodMenuService(FoodMenuRepository foodMenuRepository) {
        this.foodMenuRepository = foodMenuRepository;
    }

    /**
     * 전체 조회
     * @param pageable
     * @return
     */
    public Page<FoodMenu> findAll(Pageable pageable) {
        return foodMenuRepository.findAll(pageable);
    }

    /**
     * 상세 조회
     * @param id
     * @return
     */
    public FoodMenu findById(Long id){
        return foodMenuRepository.findById(id).orElseThrow();
    }

    /**
     * 등록 및 수정 처리
     * @param foodMenu
     */
    public void save(FoodMenu foodMenu, MultipartHttpServletRequest request) {
        if(foodMenu.getFoodMenuNo() == null || foodMenu.getFoodMenuNo() == 0){
            foodMenu.setRegistDate(DateUtil.getTodateTime());
            foodMenu.setRegistId(LoginUtil.getLoingId());
        }   else {
            foodMenu.setUpdateDate(DateUtil.getTodateTime());
            foodMenu.setUpdateId(LoginUtil.getLoingId());
        }

        foodMenuRepository.save(foodMenu);

    }

    /**
     * 삭제 처리
     * @param foodMenu
     */
    public void foodMenuDelete(FoodMenu foodMenu) {
        foodMenuRepository.delete(foodMenu);
    }

    public List<FoodMenu> findByRestaurantId(String restaurantId) {
        return foodMenuRepository.findByRestaurantId(restaurantId);
    }
}