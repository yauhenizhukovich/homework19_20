package com.gmail.supersonicleader.repository.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.gmail.supersonicleader.repository.ItemRepository;
import com.gmail.supersonicleader.repository.model.Item;
import com.gmail.supersonicleader.repository.model.StatusEnum;
import org.springframework.stereotype.Repository;

@Repository
public class ItemRepositoryImpl extends GeneralRepositoryImpl<Item> implements ItemRepository {

    @Override
    public Item add(Connection connection, Item item) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO item (name, status) VALUES (?,?)",
                Statement.RETURN_GENERATED_KEYS
        )) {
            String name = item.getName();
            statement.setString(1, name);
            StatusEnum status = item.getStatus();
            String statusName = status.name();
            statement.setString(2, statusName);
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating item failed, no rows affected.");
            }
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    Long id = generatedKeys.getLong(1);
                    item.setId(id);
                    return item;
                } else {
                    throw new SQLException("Creating item failed, no ID obtained.");
                }
            }
        }
    }

    @Override
    public List<Item> findAll(Connection connection) throws SQLException {
        try (
                PreparedStatement statement = connection.prepareStatement(
                        "SELECT id, name, status FROM item"
                )
        ) {
            List<Item> items = new ArrayList<>();
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Item item = getItem(resultSet);
                    items.add(item);
                }
                return items;
            }
        }
    }

    @Override
    public List<Item> findByStatus(Connection connection, StatusEnum status) throws SQLException {
        try (
                PreparedStatement statement = connection.prepareStatement(
                        "SELECT id, name, status FROM item WHERE status=?"
                )
        ) {
            String statusName = status.name();
            statement.setString(1, statusName);
            List<Item> items = new ArrayList<>();
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Item item = getItem(resultSet);
                    items.add(item);
                }
                return items;
            }
        }
    }

    @Override
    public void updateStatusById(Connection connection, Long id, StatusEnum updatedStatus) throws SQLException {
        try (
                PreparedStatement statement = connection.prepareStatement(
                        "UPDATE item SET status=? WHERE id=?"
                )
        ) {
            String statusName = updatedStatus.name();
            statement.setString(1, statusName);
            statement.setLong(2, id);
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Updating item status failed, no rows affected.");
            }
        }
    }

    @Override
    public int deleteByStatus(Connection connection, StatusEnum status) throws SQLException {
        try (
                PreparedStatement statement = connection.prepareStatement(
                        "DELETE FROM item WHERE status=?"
                )
        ) {
            String statusName = status.name();
            statement.setString(1, statusName);
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Deleting item failed, no rows affected.");
            }
            return affectedRows;
        }
    }

    private Item getItem(ResultSet resultSet) throws SQLException {
        Item item = new Item();
        Long id = resultSet.getLong("id");
        item.setId(id);
        String name = resultSet.getString("name");
        item.setName(name);
        String statusString = resultSet.getString("status");
        StatusEnum status = StatusEnum.valueOf(statusString);
        item.setStatus(status);
        return item;
    }

}
