package com.argon.order.service;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class MessageService {

    public String redirectMessage(Model model, String massage, String path) {

        model.addAttribute("massage", massage);
        model.addAttribute("path", path);

        return "message/redirectMessage";
    }

    public String backMessage(Model model, String massage) {

        model.addAttribute("massage", massage);

        return "message/backMessage";
    }

}
