package com.gmail.supersonicleader.service.impl;

import com.gmail.supersonicleader.repository.model.User;
import com.gmail.supersonicleader.service.UserService;
import com.gmail.supersonicleader.service.model.AppUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AppUserDetailsService implements UserDetailsService {

    private final UserService userService;

    public AppUserDetailsService(UserService userService) {this.userService = userService;}

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.getUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User is not found.");
        }
        return new AppUser(user);
    }

}
