package com.argon.order.util;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;

@RequiredArgsConstructor
public class PagingUtil {

    public static void getPaginationInfo(Model model, Page page){
        int nowPage = page.getPageable().getPageNumber() + 1;
        int startPage = ((nowPage-1)/10*10)+1;
        int endPage = ((nowPage-1)/10*10)+10;
        if(endPage > page.getTotalPages()) endPage = page.getTotalPages();

        model.addAttribute("nowPage",nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("totalPages", page.getTotalPages());
    }
}
