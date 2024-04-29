package com.argon.order.service;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;

@Service
public class MessageService {

    public String redirectMessage(Model model, String message, String path) {

        model.addAttribute("message", message);
        model.addAttribute("path", path);

        return "message/redirectMessage";
    }

    public String backMessage(Model model, String message) {

        model.addAttribute("message", message);

        return "message/backMessage";
    }

}
