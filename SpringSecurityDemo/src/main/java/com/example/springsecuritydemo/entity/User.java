package com.example.springsecuritydemo.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class User {
    private String username;
    private String password;
    // @OneToMany
    private List<String> permissions;
}
