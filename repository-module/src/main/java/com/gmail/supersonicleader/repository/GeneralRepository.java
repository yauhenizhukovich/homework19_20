package com.gmail.supersonicleader.repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface GeneralRepository<T> {

    Connection getConnection() throws SQLException;

    T add(Connection connection, T t) throws SQLException;

    List<T> findAll(Connection connection) throws SQLException;

}
