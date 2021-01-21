package com.ticketsproject.servisesImpl;

import com.ticketsproject.entities.User;
import com.ticketsproject.repository.UserRepository;
import com.ticketsproject.entities.common.UserPrinciple;
import com.ticketsproject.servises.SecurityService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SecurityServiceImpl implements SecurityService {

    private final UserRepository userRepository;

    public SecurityServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(s);
        if (user == null) throw new UsernameNotFoundException("Account with ID: " + s + ", does not exists");
        return new UserPrinciple(user);
    }
}
