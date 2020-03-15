package com.gmail.supersonicleader.service;

import com.gmail.supersonicleader.repository.model.User;

public interface UserService {

    User getUserByUsername(String username);

}
