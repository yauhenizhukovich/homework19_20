package com.gmail.supersonicleader.repository.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.gmail.supersonicleader.repository.UserRepository;
import com.gmail.supersonicleader.repository.model.RoleEnum;
import com.gmail.supersonicleader.repository.model.User;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryImpl extends GeneralRepositoryImpl<User> implements UserRepository {

    @Override
    public User getByUsername(Connection connection, String username) throws SQLException {
        try (
                PreparedStatement statement = connection.prepareStatement(
                        "SELECT username, password, role FROM user WHERE username=?"
                )
        ) {
            statement.setString(1, username);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return getUser(resultSet);
                }
            }
            return null;
        }
    }

    @Override
    public User add(Connection connection, User user) {
        throw new UnsupportedOperationException("Add action for user is not implemented");
    }

    @Override
    public List<User> findAll(Connection connection) {
        throw new UnsupportedOperationException("Find all action for user is not implemented");
    }

    private User getUser(ResultSet resultSet) throws SQLException {
        User user = new User();
        String username = resultSet.getString("username");
        user.setUsername(username);
        String password = resultSet.getString("password");
        user.setPassword(password);
        String roleName = resultSet.getString("role");
        RoleEnum role = RoleEnum.valueOf(roleName);
        user.setRole(role);
        return user;
    }

}
