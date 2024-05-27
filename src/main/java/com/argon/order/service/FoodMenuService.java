package com.argon.order.service;

import com.argon.order.domain.FoodMenu;
import com.argon.order.repository.FoodMenuRepository;
import com.argon.order.util.DateUtil;
import com.argon.order.util.FileUtil;
import com.argon.order.util.LoginUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;


@Slf4j
@RequiredArgsConstructor
@Service
public class FoodMenuService {

    @Value("${order.upload.path}")
    private String filePath;

    // foodMenuService
    private final FoodMenuRepository foodMenuRepository;

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
        foodMenu = foodMenu.toBuilder()
                .restaurantId(LoginUtil.getRestaurantId(request))
                .build();
        if(!Objects.requireNonNull(file.getOriginalFilename()).isEmpty()){
            foodMenu = foodMenu.toBuilder()
                    .foodImgName(file.getOriginalFilename())
                    .foodImgStoreName(DateUtil.getTodateTime()+uuid+"."+file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1))
                    .foodImgPath("foodMenu/")
                    .build();
            FileUtil.getFileUploadAtchmnfl(file.getInputStream(), filePath+foodMenu.getFoodImgPath(), foodMenu.getFoodImgStoreName());
        }
        if(foodMenu.getFoodMenuNo() == null || foodMenu.getFoodMenuNo() == 0){
            foodMenu = foodMenu.toBuilder()
                    .registDate(DateUtil.getTodateTime())
                    .registId(LoginUtil.getLoingId())
                    .build();
        }   else {
            foodMenu = foodMenu.toBuilder()
                    .updateDate(DateUtil.getTodateTime())
                    .updateId(LoginUtil.getLoingId())
                    .build();
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

    public List<FoodMenu> findAllByRestaurantId(String restaurantId) {
        return foodMenuRepository.findAllByRestaurantId(restaurantId);
    }
}
