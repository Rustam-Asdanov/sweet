package com.candy.sweet.controller;

import com.candy.sweet.model.Candy;
import com.candy.sweet.service.MainService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/candy-base")
@AllArgsConstructor
public class BaseController {

    private final MainService mainService;

    @GetMapping
    public String getPage(Model model){
        model.addAttribute("candy_list",mainService.getMyCandyList());
        return "base";
    }

    @GetMapping("/add-candy")
    public String getAddCandyPage(Model model){
        model.addAttribute("candy", new Candy());
        return "add-product";
    }

    @PostMapping
    public String saveCandy(
            @Valid
            @ModelAttribute("candy") Candy theCandy,
            @RequestParam("candy_image")MultipartFile multipartFile
            ){
        System.out.println(multipartFile);
        mainService.addCandy(theCandy, multipartFile);
        return "redirect:/candy-base";
    }

    @GetMapping("/delete/{id}")
    public String deleteCandyById(
            @PathVariable("id") long id
    ){
        System.out.println(id);
        mainService.deleteCandyById(id);
        return "redirect:/candy-base";
    }

    @GetMapping("/edit/{id}")
    public String editCandy(
            @PathVariable("id") long id,
            Model model
    ){
        Candy candy = mainService.getCandyById(id);
        model.addAttribute("candy", candy);
        return "add-product";
    }
}
