package com.argon.order.controller;

import com.argon.order.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class LoginController {

    // messageService
    private final MessageService messageService;

    @GetMapping("/loginForm")
    public String loginForm(){
        return "/login/loginView";
    }

    @GetMapping("/loginError")
    public String loginError(Model model){
        return messageService.backMessage(model, "아이디 또는 비밀번호를 확인해주세요.");
    }


}
