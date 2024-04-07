package com.argon.order.util;

import org.springframework.security.core.context.SecurityContextHolder;

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

}
