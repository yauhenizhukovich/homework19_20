package com.gmail.supersonicleader.web.controller;

import java.util.List;

import com.gmail.supersonicleader.service.ItemService;
import com.gmail.supersonicleader.service.model.ItemDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    private final ItemService itemService;

    public UserController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/items")
    public String getItems(Model model) {
        List<ItemDTO> items = itemService.getItemsByAuthorization();
        model.addAttribute("items", items);
        return "items";
    }

}
