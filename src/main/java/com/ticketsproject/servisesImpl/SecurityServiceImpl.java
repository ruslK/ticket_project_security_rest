package com.ticketsproject.servisesImpl;

import com.ticketsproject.dto.UserDTO;
import com.ticketsproject.entities.User;
import com.ticketsproject.exception.AccessDeniedException;
import com.ticketsproject.mapper.MapperUtil;
import com.ticketsproject.servises.SecurityService;
import com.ticketsproject.servises.UserService;
import lombok.SneakyThrows;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class SecurityServiceImpl implements SecurityService {

    private final UserService userService;
    private final MapperUtil mapperUtil;

    public SecurityServiceImpl(UserService userService, MapperUtil mapperUtil) {
        this.userService = userService;
        this.mapperUtil = mapperUtil;
    }

    @SneakyThrows
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        UserDTO user = userService.findByUserName(s);
        if (user == null) throw new UsernameNotFoundException("Account with ID: " + s + ", does not exists");
        return new org.springframework.security.core
                .userdetails.User(user.getId().toString(), user.getPassword(), this.listAuthorities(user));
    }

    private Collection<? extends GrantedAuthority> listAuthorities(UserDTO user) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        GrantedAuthority auth = new SimpleGrantedAuthority(user.getRole().getDescription());
        authorities.add(auth);
        return authorities;
    }

    @Override
    public User loadUser(String param) throws AccessDeniedException {
        UserDTO userDTO = userService.findByUserName(param);
        return mapperUtil.convert(userDTO, new User());
    }
}
