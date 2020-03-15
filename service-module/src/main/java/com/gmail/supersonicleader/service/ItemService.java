package com.gmail.supersonicleader.service;

import com.gmail.supersonicleader.repository.model.StatusEnum;
import com.gmail.supersonicleader.service.model.ItemDTO;

import java.util.List;

public interface ItemService {

    ItemDTO addItem(ItemDTO itemDTO);

    void updateStatusById(Long id, StatusEnum status);

    int deleteItemByStatus(StatusEnum status);

    List<ItemDTO> getItemsByAuthorization();

    List<ItemDTO> getAllItems();

}
