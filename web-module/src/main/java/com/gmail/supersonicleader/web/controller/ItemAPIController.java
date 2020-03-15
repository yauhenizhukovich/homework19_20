package com.gmail.supersonicleader.web.controller;

import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;

import com.gmail.supersonicleader.repository.model.StatusEnum;
import com.gmail.supersonicleader.service.ItemService;
import com.gmail.supersonicleader.service.model.ItemDTO;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/items")
public class ItemAPIController {

    private final ItemService itemService;

    public ItemAPIController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping
    public List<ItemDTO> getItems() {
        return itemService.getAllItems();
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Object saveItem(
            @RequestBody @Valid ItemDTO item,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            List<String> errors = new ArrayList<>();
            List<ObjectError> bindingResultErrors = bindingResult.getAllErrors();
            bindingResultErrors.forEach(error -> errors.add(error.getDefaultMessage()));
            return errors;
        }
        return itemService.addItem(item);
    }

    @PutMapping("/{id}")
    public String updateItemStatus(
            @PathVariable Long id,
            @RequestBody ItemDTO item
    ) {
        StatusEnum status = item.getStatus();
        itemService.updateStatusById(id, status);
        return "Item successfully update.";
    }

    @DeleteMapping
    public String deleteItemByStatus(
            @RequestBody ItemDTO item
    ) {
        StatusEnum status = item.getStatus();
        int countOfDeletedItems = itemService.deleteItemByStatus(status);
        return "Successfully deleted " + countOfDeletedItems + " items";
    }

}
