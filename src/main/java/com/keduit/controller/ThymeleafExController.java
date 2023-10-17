package com.keduit.controller;

import com.keduit.dto.ItemDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/thymeleaf")
public class ThymeleafExController {

    @GetMapping(value = "/ex01")
    public String thex01(Model model){
        model.addAttribute("data", "타임리프 예제 입니다");
        return "thymeleafEx/thex01";
    }

    @GetMapping(value = "/ex02")
    public String thymeleafEx02(Model model){
        ItemDto itemDto = new ItemDto();
        itemDto.setItemDetail("상품 상세 설명");
        itemDto.setItemNm("테스트 상품1");
        itemDto.setPrice(10000);
        itemDto.setRegTime(LocalDateTime.now());

        model.addAttribute("itemDto", itemDto);
        return "thymeleafEx/thex02";
    }

    @GetMapping(value = "/ex03")
    public String thymeex03(Model model){
        List<ItemDto> itemDtoList = new ArrayList<>();

        for (int i = 1; i<=10; i++){
            ItemDto itemDto = new ItemDto();
            itemDto.setItemDetail("상품 상세 설명 " + i);
            itemDto.setItemNm("테스트 상품 " + i);
            itemDto.setPrice(1000*i);
            itemDto.setRegTime(LocalDateTime.now());
            itemDtoList.add(itemDto);
        }

        model.addAttribute("itemDtoList", itemDtoList);
        return "thymeleafEx/thex03";
    }

    @GetMapping(value = "/ex04")
    public String thex04(Model model){
        List<ItemDto> itemDtoList = new ArrayList<>();

        for (int i = 1; i<=10; i++){
            ItemDto itemDto = new ItemDto();
            itemDto.setItemDetail("상품 상세 설명 " + i);
            itemDto.setItemNm("테스트 상품 " + i);
            itemDto.setPrice(1000*i);
            itemDto.setRegTime(LocalDateTime.now());
            itemDtoList.add(itemDto);
        }

        model.addAttribute("itemDtoList", itemDtoList);
        return "thymeleafEx/thex04";
    }

    @GetMapping(value = "/ex05")
    public String thex05(){
        return "thymeleafEx/thex05";
    }

    @GetMapping(value = "/ex06")
    public String thymeleafEx06(String param1, String param2, Model model){
        model.addAttribute("param1", param1);
        model.addAttribute("param2", param2);
        return "thymeleafEx/thex06";
    }

    @GetMapping(value = "/ex07")
    public String thex07(){
        return "thymeleafEx/thex07";
    }

    @GetMapping(value = "/exin")
    public String exin(RedirectAttributes redirectAttributes){
        System.out.println("------exinline-------");
        return "thymeleafEx/thex07";
    }
}
