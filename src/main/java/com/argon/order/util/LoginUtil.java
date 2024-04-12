package com.argon.order.util;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;

@RequiredArgsConstructor
public class LoginUtil {

    public static boolean isLogin(){
        boolean result = true;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof String){
            result = false;
        }

        return result;
    }

    public static String getLoingId(){
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    public static String getRestaurantId(HttpServletRequest request){
        return (String) request.getSession().getAttribute("loginRestaurantId");
    }

    public static boolean getLoginIsAdmin(){
        if(SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().filter(o -> o.getAuthority().equals("ROLE_ADMIN")).findAny().isPresent()) return true;
        return false;
    }

}
