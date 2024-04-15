package com.argon.order.service;

import com.argon.order.domain.FoodMenu;
import com.argon.order.repository.FoodMenuRepository;
import com.argon.order.util.DateUtil;
import com.argon.order.util.FileUtil;
import com.argon.order.util.LoginUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;
import java.util.UUID;

@Service
@Slf4j
public class FoodMenuService {

    @Value("${order.upload.path}")
    private String filePath;

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
    public Page<FoodMenu> findByRestaurantId(Pageable pageable, String restaurantId) {
        return foodMenuRepository.findByRestaurantId(pageable, restaurantId);
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
    public void save(FoodMenu foodMenu, MultipartHttpServletRequest request) throws IOException {
        UUID uuid = UUID.randomUUID();
        MultipartFile file = request.getFiles("fileUpload").get(0);
        foodMenu.setRestaurantId(LoginUtil.getRestaurantId(request));
        foodMenu.setFoodImgName(file.getOriginalFilename());
        foodMenu.setFoodImgStoreName(DateUtil.getTodateTime()+uuid+"."+file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1));
        foodMenu.setFoodImgPath("/upload/foodMenu/");
        FileUtil.getFileUploadAtchmnfl(file.getInputStream(), filePath+foodMenu.getFoodImgPath(), foodMenu.getFoodImgStoreName());
        if(foodMenu.getFoodMenuNo() == null || foodMenu.getFoodMenuNo() == 0){
            foodMenu.setRegistDate(DateUtil.getTodateTime());
            foodMenu.setRegistId(LoginUtil.getLoingId());
        }   else {
            foodMenu.setUpdateDate(DateUtil.getTodateTime());
            foodMenu.setUpdateId(LoginUtil.getLoingId());
        }
        foodMenu.setRegistId(LoginUtil.getRestaurantId(request));
        foodMenuRepository.save(foodMenu);
    }

    /**
     * 삭제 처리
     * @param foodMenu
     */
    public void foodMenuDelete(FoodMenu foodMenu) {
        foodMenuRepository.delete(foodMenu);
    }

}
