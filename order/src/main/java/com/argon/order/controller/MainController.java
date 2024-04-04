package com.argon.order.controller;

import com.argon.order.service.MessageService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class MainController {

    // messageService
    private final MessageService messageService;

    @GetMapping("/")
    public String index(){
        return "redirect:/main";
    }

    @GetMapping("/main")
    public String main(){return "/admin/main";
    }

    @GetMapping("/loginForm")
    public String loginForm(){
        return "/login/loginView";
    }

    @GetMapping("/loginError")
    public String loginError(Model model){
        return messageService.backMessage(model, "아이디 또는 비밀번호를 확인해주세요.");
    }

}
