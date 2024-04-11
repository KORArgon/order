package com.argon.order.controller;

import com.argon.order.domain.Restaurant;
import com.argon.order.service.RestaurantService;
import com.argon.order.service.MessageService;
import com.argon.order.util.PagingUtil;
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

@Controller
@RequiredArgsConstructor
public class RestaurantController {

    // restaurantService
    private final RestaurantService restaurantService;

    // messageService
    private final MessageService messageService;

    /**
     * 식당 목록 조회
     * @param model
     * @return
     */
    @GetMapping("/restaurantListForm")
    public String restaurantListForm(Model model, @PageableDefault(page=0, size=10, sort="registDate", direction = Sort.Direction.DESC) Pageable pageable){
        Page<Restaurant> restaurantList = restaurantService.findByMemberId(pageable);
        model.addAttribute("totCnt",restaurantList.getTotalElements());
        model.addAttribute("restaurantList", restaurantList);
        PagingUtil.getPaginationInfo(model, restaurantList);
        return "/restaurant/restaurantList";
    }

    /**
     * 식당 상세 조회
     * @param restaurant
     * @param model
     * @return
     */
    @GetMapping("/restaurantViewForm")
    public String restaurantViewForm(Restaurant restaurant, Model model){
        Restaurant result = restaurantService.findByRestaurantId(restaurant.getRestaurantId());
        model.addAttribute("restaurant", result);
        return "/restaurant/restaurantView";
    }

    /**
     * 식당 등록 페이지
     * @return
     */
    @GetMapping("/restaurantRegistForm")
    public String restaurantRegistForm(){
        return "/restaurant/restaurantRegist";
    }

    /**
     * 식당 등록 처리
     * @param restaurant
     * @param model
     * @return
     */
    @PostMapping("/restaurantRegist")
    public String restaurantRegist(Restaurant restaurant, Model model){
        restaurantService.save(restaurant);
        return messageService.redirectMessage(model, "등록을 완료했습니다.", "/restaurantListForm");
    }

    /**
     * 식당 수정 페이지
     * @param restaurant
     * @param model
     * @return
     */
    @GetMapping("/restaurantUpdateForm")
    public String restaurantUpdateForm(Restaurant restaurant, Model model){
        Restaurant result = restaurantService.findByRestaurantId(restaurant.getRestaurantId());
        model.addAttribute("restaurant", result);
        return "/restaurant/restaurantUpdate";
    }

    /**
     * 식당 수정 처리
     * @param restaurant
     * @param model
     * @return
     */
    @PutMapping("/restaurantUpdate")
    public String restaurantUpdate(Restaurant restaurant, Model model){
        restaurantService.save(restaurant);
        return messageService.redirectMessage(model, "수정을 완료했습니다.", "/restaurantListForm");
    }

    /**
     * 식당 삭제 처리
     * @param restaurant
     * @param model
     * @return
     */
    @DeleteMapping("/restaurantDelete")
    public String restaurantDelete(Restaurant restaurant, Model model){
        restaurantService.restaurantDelete(restaurant);
        return messageService.redirectMessage(model, "삭제를 완료했습니다.", "/restaurantListForm");
    }

}
