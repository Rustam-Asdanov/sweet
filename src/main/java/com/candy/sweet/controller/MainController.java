package com.candy.sweet.controller;

import com.candy.sweet.model.Account;
import com.candy.sweet.model.Candy;
import com.candy.sweet.model.Order;
import com.candy.sweet.service.MainService;
import com.candy.sweet.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/candy-shop")
@AllArgsConstructor
public class MainController {

    private final MainService mainService;
    private final OrderService orderService;

    private List<Candy> purchaseList = new ArrayList<>();

    @GetMapping
    public String getPage(Model model, Authentication authentication){

        model.addAttribute("candy_list", mainService.getCandyList());
        model.addAttribute("purchaseList", purchaseList);

        if(authentication != null){
            if(authentication.getName().equalsIgnoreCase("admin")){
                model.addAttribute("isAdmin",true);
            }
        }


        return "shop";
    }

    @GetMapping("/sign-up")
    public String getSignUpPage(Model model){
        model.addAttribute("account", new Account());
        return "signup";
    }

    @PostMapping
    public String saveAccount(
            @ModelAttribute("account") Account theAccount
    ){
        mainService.saveAccount(theAccount);

        return "redirect:/login";
    }

    @GetMapping("/info/{id}")
    public String getInfoPage(
            @PathVariable("id") long id,
            Model model
    ){
        Candy theCandy = mainService.getCandyById(id);
        model.addAttribute("candy_info", theCandy);
        System.out.println("Candy id" + id);
        return "info-page";
    }

    @GetMapping("/basket/buy/{id}")
    public String buyProcess(
            @PathVariable("id") long id
    ){
        Candy candy = mainService.getCandyById(id);

        if(!purchaseList.contains(candy)){
            purchaseList.add(candy);
        }


        System.out.println(purchaseList);

        return "redirect:/candy-shop";
    }

    @GetMapping("/basket/delete/{id}")
    public String deleteProcess(
            @PathVariable("id") long id
    ){
        purchaseList.removeIf(candy -> candy.getId() == id);

        return "redirect:/candy-shop";
    }

    @GetMapping("/purchase")
    public String getPurchasePage(Model model){
        model.addAttribute("order", new Order());
        return "purchase-page";
    }

    @PostMapping("/finishPurchase")
    public String finishPurchase(
            @Valid @ModelAttribute("order") Order order,
            Errors errors
    ){
        if(errors.hasErrors()){
            return "purchase-page";
        }

        order.setOrderedCandyList(purchaseList);
        orderService.saveOrder(order);
        purchaseList.clear();

        return "redirect:/candy-shop";
    }

    @GetMapping("/myorders")
    public String getMyOrdersPage(Model model){
        model.addAttribute("orderList", orderService.getAllOrders());

        return "myorders-page";
    }
}
