package com.argon.order.controller;

import com.argon.order.service.MessageService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class LoginController {

    // messageService
    private final MessageService messageService;

    @GetMapping("/loginForm")
    public String loginForm(){
        return "login/loginView";
    }

    @GetMapping("/loginError")
    public String loginError(Model model){
        return messageService.backMessage(model, "아이디 또는 비밀번호를 확인해주세요.");
    }

    @ResponseBody
    @PostMapping("/loginRestaurant")
    public void loginRestaurant(@RequestParam(value="restaurantId") String restaurantId, HttpSession session) {
        session.setAttribute("loginRestaurantId", restaurantId);
    }

}
