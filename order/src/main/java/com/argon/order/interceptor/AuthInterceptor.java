package com.argon.order.interceptor;

import com.argon.order.domain.Member;
import com.argon.order.repository.MemberRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {

    private final MemberRepository memberRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Member member = memberRepository.findByMemberId(authentication.getName());
        ModelMap model = new ModelMap();
        if(modelAndView != null && member != null){
            model.addAttribute("loginId", member.getMemberId());
            model.addAttribute("loginName", member.getName());
            model.addAttribute("loginGrade", member.getGrade());
            modelAndView.addObject("loginMap", model);
        }
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

}
