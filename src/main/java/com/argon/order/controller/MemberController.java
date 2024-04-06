package com.argon.order.controller;

import com.argon.order.domain.Member;
import com.argon.order.service.MemberService;
import com.argon.order.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class MemberController {

    // memberService
    private final MemberService memberService;

    // messageService
    private final MessageService messageService;

    /**
     * 회원 등록 페이지
     * @return
     */
    @GetMapping("/memberRegistForm")
    public String memberRegistForm(){
        return "/member/memberRegist";
    }

    @PostMapping("/memberRegist")
    public String memberRegist(Model model, Member member, Principal principal){
        memberService.save(member, principal);
        return messageService.redirectMessage(model, "등록을 완료했습니다.", "/");
    }
}
