package com.gmail.supersonicleader.service.impl;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.gmail.supersonicleader.repository.ItemRepository;
import com.gmail.supersonicleader.repository.model.Item;
import com.gmail.supersonicleader.repository.model.RoleEnum;
import com.gmail.supersonicleader.repository.model.StatusEnum;
import com.gmail.supersonicleader.service.ItemService;
import com.gmail.supersonicleader.service.model.ItemDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class ItemServiceImpl implements ItemService {

    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private final ItemRepository itemRepository;

    public ItemServiceImpl(ItemRepository itemRepository) {this.itemRepository = itemRepository;}

    @Override
    public ItemDTO addItem(ItemDTO itemDTO) {
        try (Connection connection = itemRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                Item item = convertDTOToDatabaseObject(itemDTO);
                item = itemRepository.add(connection, item);
                itemDTO = convertDatabaseObjectToDTO(item);
                connection.commit();
                return itemDTO;
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public List<ItemDTO> getItemsByAuthorization() {
        try (Connection connection = itemRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                List<Item> itemsDatabase = new ArrayList<>();
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
                for (GrantedAuthority authority : authorities) {
                    RoleEnum roleEnum = getRoleEnum(authority);
                    itemsDatabase = getItemsByRole(connection, roleEnum);
                }
                List<ItemDTO> itemsDTO = itemsDatabase.stream()
                        .map(this::convertDatabaseObjectToDTO)
                        .collect(Collectors.toList());
                connection.commit();
                return itemsDTO;
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return Collections.emptyList();
    }

    @Override
    public List<ItemDTO> getAllItems() {
        try (Connection connection = itemRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                List<Item> items = itemRepository.findAll(connection);
                List<ItemDTO> itemsDTO = items.stream()
                        .map(this::convertDatabaseObjectToDTO)
                        .collect(Collectors.toList());
                connection.commit();
                return itemsDTO;
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return Collections.emptyList();
    }

    @Override
    public void updateStatusById(Long id, StatusEnum status) {
        try (Connection connection = itemRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                itemRepository.updateStatusById(connection, id, status);
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public int deleteItemByStatus(StatusEnum status) {
        try (Connection connection = itemRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                int countOfDeletedItems = itemRepository.deleteByStatus(connection, status);
                connection.commit();
                return countOfDeletedItems;
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return 0;
    }

    private List<Item> getItemsByRole(Connection connection, RoleEnum roleEnum) throws SQLException {
        List<Item> itemsDatabase;
        switch (roleEnum) {
            case ADMIN:
                itemsDatabase = itemRepository.findAll(connection);
                break;
            case USER:
                itemsDatabase = itemRepository.findByStatus(connection, StatusEnum.COMPLETED);
                break;
            default:
                throw new UnsupportedOperationException("Invalid role.");
        }
        return itemsDatabase;
    }

    private RoleEnum getRoleEnum(GrantedAuthority authority) {
        String authorityRole = authority.getAuthority();
        String role = authorityRole.replace("ROLE_", "");
        return RoleEnum.valueOf(role);
    }

    private Item convertDTOToDatabaseObject(ItemDTO itemDTO) {
        Item item = new Item();
        String name = itemDTO.getName();
        item.setName(name);
        StatusEnum status = itemDTO.getStatus();
        item.setStatus(status);
        return item;
    }

    private ItemDTO convertDatabaseObjectToDTO(Item item) {
        ItemDTO itemDTO = new ItemDTO();
        String name = item.getName();
        itemDTO.setName(name);
        StatusEnum status = item.getStatus();
        itemDTO.setStatus(status);
        Long id = item.getId();
        itemDTO.setId(id);
        return itemDTO;
    }

}
