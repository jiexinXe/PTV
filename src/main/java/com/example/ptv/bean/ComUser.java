package com.example.ptv.bean;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class ComUser implements UserDetails {
    private String username;

    private String password;

    private int userId;

    private List<GrantedAuthority> authorities;
    public ComUser(int userId, String username, String password, List<GrantedAuthority> authorities) {
        this.userId=userId;
        this.username=username;
        this.password=password;
        this.authorities=authorities;
    }



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    public int getUserId(){
        return this.userId;
    }

    public void setUserId(int userId){
        this.userId= userId;
    }
    public void setUsername(String username){
        this.username=username;
    }

    public void setPassword(String password){
        this.password=password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
