package com.ticketsproject.controller;

import com.ticketsproject.annotation.DefaultExceptionMessage;
import com.ticketsproject.dto.UserDTO;
import com.ticketsproject.entities.User;
import com.ticketsproject.entities.common.AuthenticationRequest;
import com.ticketsproject.entities.ResponseWrapper;
import com.ticketsproject.exception.AccessDeniedException;
import com.ticketsproject.exception.TicketingProjectException;
import com.ticketsproject.mapper.MapperUtil;
import com.ticketsproject.servises.UserService;
import com.ticketsproject.util.JWTUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name="Authenticate Controller", description = "Authentication API")
public class AuthController {

    private final JWTUtil jwtUtil;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final MapperUtil mapperUtil;


    public AuthController(JWTUtil jwtUtil, UserService userService, AuthenticationManager authenticationManager, MapperUtil mapperUtil) {
        this.jwtUtil = jwtUtil;
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.mapperUtil = mapperUtil;
    }

    @PostMapping("/authenticate")
    @DefaultExceptionMessage(defaultMessage = "Bad Credential")
    @Operation(summary = "Login to Application", description = "Authentication of account")
    public ResponseEntity<ResponseWrapper> getToken(@RequestBody AuthenticationRequest body) throws TicketingProjectException, AccessDeniedException {
        String password = body.getPassword();
        String username = body.getUsername();
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(username, password);

//        if(!authenticationToken.isAuthenticated()) {
//            throw new AccessDeniedException("Credential are not correct");
//        }

        authenticationManager.authenticate(authenticationToken);

        UserDTO foundUser = userService.findByUserName(username);
        if(!foundUser.isEnabled()) {
            throw new TicketingProjectException("Account is not VERIFIED, Please Verify your account!");
        }

        User user = mapperUtil.convert(foundUser, new User());
        String jwtUtil = this.jwtUtil.generateToken(user);
        return ResponseEntity.ok(new ResponseWrapper("Login Successful!", jwtUtil));
    }
}
