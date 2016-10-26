package com.example.business;

import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Data
@Builder
public class User {
    private String name;
    private String token;
    private List<String> roles;

    public List<GrantedAuthority> getGrantedAuthorities() {
        return roles.stream().map(SimpleGrantedAuthority::new).collect(toList());
    }
}