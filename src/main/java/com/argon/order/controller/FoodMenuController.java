package com.argon.order.controller;

import com.argon.order.util.LoginUtil;
import com.argon.order.util.PagingUtil;
import com.argon.order.domain.FoodMenu;
import com.argon.order.service.FoodMenuService;
import com.argon.order.service.MessageService;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class FoodMenuController {

    // foodMenuService
    private final FoodMenuService foodMenuService;

    // messageService
    private final MessageService messageService;

    /**
     * 음식메뉴 목록 조회
     * @param model
     * @return
     */
    @GetMapping("/foodMenuListForm")
    public String foodMenuListForm(Model model, HttpServletRequest request,
                                   @PageableDefault(page=0, size=10, sort="foodMenuNo", direction = Sort.Direction.DESC) Pageable pageable){

        Page<FoodMenu> foodMenuList = foodMenuService.findByRestaurantId(pageable, LoginUtil.getRestaurantId(request));
        model.addAttribute("totCnt",foodMenuList.getTotalElements());
        model.addAttribute("foodMenuList", foodMenuList);
        PagingUtil.getPaginationInfo(model, foodMenuList);

        return "foodMenu/foodMenuList";
    }

    /**
     * 음식메뉴 상세 조회
     * @param foodMenu
     * @param model
     * @return
     */
    @GetMapping("/foodMenuViewForm")
    public String foodMenuViewForm(FoodMenu foodMenu, Model model){
        FoodMenu result = foodMenuService.findById(foodMenu.getFoodMenuNo());
        model.addAttribute("foodMenu", result);
        return "foodMenu/foodMenuView";
    }

    /**
     * 음식메뉴 등록 페이지
     * @return
     */
    @GetMapping("/foodMenuRegistForm")
    public String foodMenuRegistForm(){
        return "foodMenu/foodMenuRegist";
    }

    /**
     * 음식메뉴 등록 처리
     * @param foodMenu
     * @param model
     * @return
     */
    @PostMapping("/foodMenuRegist")
    public String foodMenuRegist(FoodMenu foodMenu, Model model, MultipartHttpServletRequest request){
        try {
            foodMenuService.save(foodMenu, request);
        } catch (IOException e) {
            return messageService.backMessage(model, "등록에 실패했습니다.");
        }
        return messageService.redirectMessage(model, "등록을 완료했습니다.", "/foodMenuListForm");
    }

    /**
     * 음식메뉴 수정 페이지
     * @param foodMenu
     * @param model
     * @return
     */
    @GetMapping("/foodMenuUpdateForm")
    public String foodMenuUpdateForm(FoodMenu foodMenu, Model model){
        FoodMenu result = foodMenuService.findById(foodMenu.getFoodMenuNo());
        model.addAttribute("foodMenu", result);
        return "foodMenu/foodMenuUpdate";
    }

    /**
     * 음식메뉴 수정 처리
     * @param foodMenu
     * @param model
     * @return
     */
    @PutMapping("/foodMenuUpdate")
    public String foodMenuUpdate(FoodMenu foodMenu, Model model, MultipartHttpServletRequest request){
        try {
            foodMenuService.save(foodMenu, request);
        } catch (IOException e) {
            return messageService.backMessage(model, "수정에 실패했습니다.");
        }
        return messageService.redirectMessage(model, "수정을 완료했습니다.", "/foodMenuListForm");
    }

    /**
     * 음식메뉴 삭제 처리
     * @param foodMenu
     * @param model
     * @return
     */
    @DeleteMapping("/foodMenuDelete")
    public String foodMenuDelete(FoodMenu foodMenu, Model model){
        foodMenuService.foodMenuDelete(foodMenu);
        return messageService.redirectMessage(model, "삭제를 완료했습니다.", "/foodMenuListForm");
    }

}
