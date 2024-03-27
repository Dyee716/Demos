package com.example.springsecuritydemo.service;

import com.example.springsecuritydemo.entity.User;
import com.example.springsecuritydemo.repository.UserRepository;
import com.example.springsecuritydemo.security.AuthUserDetail;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private UserRepository userRepository;

    private List<GrantedAuthority> getAuthoritiesFromUser(User user) {
        List<GrantedAuthority> userAuthorities = new ArrayList<>();

        for (String permission : user.getPermissions()) {
            userAuthorities.add(new SimpleGrantedAuthority(permission));
        }

        return userAuthorities;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.loadUserByUsername(username);

        if (!userOptional.isPresent()) {
            throw new UsernameNotFoundException("Username does not exist");
        }

        User user = userOptional.get(); // load database user

        return AuthUserDetail.builder() // build spring security's userDetail
                .username(user.getUsername())
                .password(new BCryptPasswordEncoder().encode(user.getPassword()))
                .authorities(getAuthoritiesFromUser(user))
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .enabled(true)
                .build();
    }
}
