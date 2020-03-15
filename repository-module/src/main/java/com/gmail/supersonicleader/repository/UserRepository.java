package com.gmail.supersonicleader.repository;

import java.sql.Connection;
import java.sql.SQLException;

import com.gmail.supersonicleader.repository.model.User;

public interface UserRepository extends GeneralRepository<User> {

    User getByUsername(Connection connection, String username) throws SQLException;

}
