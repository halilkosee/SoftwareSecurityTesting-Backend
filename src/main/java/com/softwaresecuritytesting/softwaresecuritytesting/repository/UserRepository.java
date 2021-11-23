package com.softwaresecuritytesting.softwaresecuritytesting.repository;

import com.softwaresecuritytesting.softwaresecuritytesting.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
