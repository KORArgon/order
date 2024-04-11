package com.argon.order.controller;

import com.argon.order.domain.Member;
import com.argon.order.service.MemberService;
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

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class MemberController {

    // memberService
    private final MemberService memberService;

    // messageService
    private final MessageService messageService;

    /**
     * 회원 목록 조회
     * @param model
     * @return
     */
    @GetMapping("/memberListForm")
    public String memberListForm(Model model, @PageableDefault(page=0, size=10, sort="registDate", direction = Sort.Direction.DESC) Pageable pageable){
        Page<Member> memberList = memberService.findAll(pageable);
        model.addAttribute("totCnt",memberList.getTotalElements());
        model.addAttribute("memberList", memberList);
        PagingUtil.getPaginationInfo(model, memberList);
        return "/member/memberList";
    }

    /**
     * 회원 상세 조회
     * @param member
     * @param model
     * @return
     */
    @GetMapping("/memberViewForm")
    public String memberViewForm(Member member, Model model){
        Member result = memberService.findById(member.getMemberId());
        model.addAttribute("member", result);
        return "/member/memberView";
    }

    /**
     * 회원 등록 페이지
     * @return
     */
    @GetMapping("/memberRegistForm")
    public String memberRegistForm(){
        return "/member/memberRegist";
    }

    /**
     * 회원 등록 처리
     * @param member
     * @param model
     * @return
     */
    @PostMapping("/memberRegist")
    public String memberRegist(Member member, Model model, Principal principal){
        memberService.save(member, principal, "insert");
        return messageService.redirectMessage(model, "등록을 완료했습니다.", "/memberListForm");
    }

    /**
     * 회원 수정 페이지
     * @param member
     * @param model
     * @return
     */
    @GetMapping("/memberUpdateForm")
    public String memberUpdateForm(Member member, Model model){
        Member result = memberService.findById(member.getMemberId());
        model.addAttribute("member", result);
        return "/member/memberUpdate";
    }

    /**
     * 회원 수정 처리
     * @param member
     * @param model
     * @return
     */
    @PutMapping("/memberUpdate")
    public String memberUpdate(Member member, Model model, Principal principal){
        memberService.save(member, principal, "update");
        return messageService.redirectMessage(model, "수정을 완료했습니다.", "/memberListForm");
    }

    /**
     * 회원 삭제 처리
     * @param member
     * @param model
     * @return
     */
    @DeleteMapping("/memberDelete")
    public String memberDelete(Member member, Model model){
        memberService.memberDelete(member);
        return messageService.redirectMessage(model, "삭제를 완료했습니다.", "/memberListForm");
    }

}
