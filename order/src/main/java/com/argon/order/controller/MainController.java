package com.argon.order.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class MainController {

    @GetMapping("/")
    public String index(){
        return "redirect:/main";
    }

    @GetMapping("/main")
    public String main(){
        return "/admin/main";
    }

    @GetMapping("/loginForm")
    public String loginForm(){
        return "/login/loginView";
    }

}
