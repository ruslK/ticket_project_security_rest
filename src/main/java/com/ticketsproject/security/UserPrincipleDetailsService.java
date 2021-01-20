package com.ticketsproject.security;

import com.ticketsproject.entities.User;
import com.ticketsproject.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserPrincipleDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserPrincipleDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(s);
        if (user == null) throw new UsernameNotFoundException("Account with ID: " + s + ", does not exists");
        return new UserPrinciple(user);
    }
}
