package com.argon.order.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MessageController {

    @GetMapping("/backMessageUnicode")
    public String backMessage(Model model, String message){

        StringBuffer result = new StringBuffer();

        for(int i=0; i<message.length(); i++){
            if(message.charAt(i) == '/' &&  message.charAt(i+1) == 'u'){
                Character c = (char)Integer.parseInt(message.substring(i+2, i+6), 16);
                result.append(c);
                i+=5;
            }else{
                result.append(message.charAt(i));
            }
        }

        model.addAttribute("message", result.toString());
        return "message/backMessage";
    }

}
