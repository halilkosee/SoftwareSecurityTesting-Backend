package com.softwaresecuritytesting.softwaresecuritytesting.service;

import com.softwaresecuritytesting.softwaresecuritytesting.model.User;

public interface UserService {
    void save(User user);

    User findByUsername(String username);
}
