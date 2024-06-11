package com.argon.order.config;

import com.argon.order.interceptor.LoginInterceptor;
import com.argon.order.interceptor.RestaurantInterceptor;
import com.argon.order.repository.MemberRepository;
import com.argon.order.repository.RestaurantRepository;
import com.argon.order.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

    @Value("${order.upload.path}")
    private String filePath;

    private final MemberRepository memberRepository;
    private final RestaurantRepository restaurantRepository;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor(memberRepository, restaurantRepository))
                .excludePathPatterns("/css/**", "/images/**", "/js/**");

        registry.addInterceptor(new RestaurantInterceptor())
                .addPathPatterns("/foodMenu**","/orderHistory**");
    }

    @Override
    public void  addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(filePath+"**") // --1
                .addResourceLocations("file://"+filePath); //--2
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:8080");
    }

}
