package com.manuscript.infrastructure.firebase.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserAuthModel implements Authentication {

    private Boolean isAuthenticated;
    private String uid;
    List<SimpleGrantedAuthority> authorityList;

    public UserAuthModel(String uid, ArrayList<SimpleGrantedAuthority> authorities) {
        this.uid = uid;
        authorityList = authorities;
        setAuthenticated(true);
    }


    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    @Override
    public Collection<SimpleGrantedAuthority> getAuthorities() {
        return authorityList;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }

    @Override
    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    @Override
    public void setAuthenticated(boolean b) throws IllegalArgumentException {
        isAuthenticated = b;
    }

    @Override
    public String getName() {
        return null;
    }

    public Boolean hasAuth(String role) {
        for (SimpleGrantedAuthority authority : authorityList) {
            if (authority.getAuthority().equals(role)) {
                return true;
            }
        }
        return false;
    }


}
