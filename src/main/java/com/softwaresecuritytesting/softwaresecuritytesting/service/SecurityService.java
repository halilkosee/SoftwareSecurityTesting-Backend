package com.softwaresecuritytesting.softwaresecuritytesting.service;

public interface SecurityService {
    boolean isAuthenticated();
    void autoLogin(String username, String password);
}
