package com.example.springsecuritydemo.repository;

import com.example.springsecuritydemo.entity.User;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {
    private final List<User> users = Arrays.asList(
            new User("reader", "reader", Arrays.asList("read")),
            new User("writer", "writer", Arrays.asList("write")),
            new User("admin", "admin", Arrays.asList("delete", "read", "update", "write"))
    );

    public Optional<User> loadUserByUsername(String username) {
        return users.stream().filter(user -> username.equals(user.getUsername())).findAny();
    }
}
