package com.gmail.supersonicleader.repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.gmail.supersonicleader.repository.model.Item;
import com.gmail.supersonicleader.repository.model.StatusEnum;

public interface ItemRepository extends GeneralRepository<Item> {

    List<Item> findByStatus(Connection connection, StatusEnum completed) throws SQLException;

    void updateStatusById(Connection connection, Long id, StatusEnum updatedStatus) throws SQLException;

    int deleteByStatus(Connection connection, StatusEnum completed) throws SQLException;

}
