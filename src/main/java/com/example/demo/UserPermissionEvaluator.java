package com.example.demo;

import org.springframework.security.core.Authentication;

import java.io.Serializable;

public class UserPermissionEvaluator implements org.springframework.security.access.PermissionEvaluator {
    @Override
    public boolean hasPermission(Authentication authentication, Object o, Object o1) {
        return false;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable serializable, String s, Object o) {
        return false;
    }
}
