package com.gmail.supersonicleader.repository.impl;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;

import com.gmail.supersonicleader.repository.GeneralRepository;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class GeneralRepositoryImpl<T> implements GeneralRepository<T> {

    @Autowired
    private DataSource dataSource;

    @Override
    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

}
