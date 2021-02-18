package com.ticketsproject.controller;

import com.ticketsproject.dto.EmailDto;
import com.ticketsproject.dto.UserDTO;
import com.ticketsproject.entities.ConfirmationToken;
import com.ticketsproject.entities.ResponseWrapper;
import com.ticketsproject.entities.User;
import com.ticketsproject.exception.TicketingProjectException;
import com.ticketsproject.mapper.MapperUtil;
import com.ticketsproject.servises.ConfirmationTokenService;
import com.ticketsproject.servises.RoleService;
import com.ticketsproject.servises.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@Tag(name = "User Controller", description = "API for User")
public class UserController {

    @Value("${app.local-url}")
    private String BASE_URL;

    private final RoleService roleService;
    private final UserService userService;
    private final ConfirmationTokenService confirmationTokenService;
    private final MapperUtil mapperUtil;


    public UserController(RoleService roleService, UserService userService, ConfirmationTokenService confirmationTokenService, MapperUtil mapperUtil) {
        this.roleService = roleService;
        this.userService = userService;
        this.confirmationTokenService = confirmationTokenService;
        this.mapperUtil = mapperUtil;
    }

    @PostMapping("/create-user")
    @Operation(summary = "Create an User", description = "Creating a new User")
    @PreAuthorize("hasAnyAuthority('Admin')")
    public ResponseEntity<ResponseWrapper> postUsers(@RequestBody UserDTO newUser) throws TicketingProjectException {
        UserDTO createUser = userService.save(newUser);

        this.sendEmail(this.createEmail(createUser));

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ResponseWrapper.builder()
                        .message("User created")
                        .code(201)
                        .success(true)
                        .data(userService.findByUserName(newUser.getUserName()))
                        .build());
    }

    @GetMapping
    @Operation(summary = "Get List of Users", description = "Get List of all Users in the Application")
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<ResponseWrapper> getAllUsers() {
        return ResponseEntity
                .ok(new ResponseWrapper("List of the Users", userService.listAllUsers()));
    }


    @GetMapping("/{username}")
    @Operation(summary = "Read User", description = "Read Specific User Info")
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<ResponseWrapper> readByUserName(@PathVariable("username") String username) {
        return ResponseEntity
                .ok(new ResponseWrapper("User retrieved", userService.findByUserName(username)));
    }


    @PutMapping
    @Operation(summary = "Update User", description = "Update Specific User Info")
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<ResponseWrapper> updateUser(@RequestBody UserDTO user) throws TicketingProjectException {
        return ResponseEntity
                .ok(new ResponseWrapper("User updated", userService.update(user)));
    }

    @DeleteMapping("/{username}")
    @Operation(summary = "Delete User", description = "Delete Specific User Info")
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<ResponseWrapper> deleteUser(@PathVariable("username") String username) throws TicketingProjectException {
        userService.delete(username);
        return ResponseEntity
                .ok(new ResponseWrapper("User deleted"));
    }

    @GetMapping("/role")
    @Operation(summary = "Get Users By Role", description = "Get Users Role Description")
    @PreAuthorize("hasAnyAuthority('Admin', 'Manager')")
    public ResponseEntity<ResponseWrapper> readByRole(@RequestParam("role") String role) {
        return ResponseEntity
                .ok(new ResponseWrapper("Users for Role: " + role, userService.listAllByRole(role)));
    }


    private EmailDto createEmail(UserDTO userDTO) {
        User user = mapperUtil.convert(userDTO, new User());
        ConfirmationToken confirmationToken = new ConfirmationToken(user);
        confirmationToken.setIsDeleted(false);
        ConfirmationToken createToken = confirmationTokenService.save(confirmationToken);
        return EmailDto.builder()
                .emailTo(user.getUserName())
                .token(createToken.getToken())
                .emailFrom("cybertekschool.email@gmail.com")
                .subject("Confirm your email")
                .message("To Confirm your account, please click here")
                .url(BASE_URL + "/confirmation?token=")
                .build();
    }

    public void sendEmail(EmailDto emailDto) {
        SimpleMailMessage sm = new SimpleMailMessage();
        sm.setTo(emailDto.getEmailTo());
        sm.setSubject(emailDto.getSubject());
        sm.setFrom(emailDto.getEmailFrom());
        sm.setText(emailDto.getMessage() + " " + emailDto.getUrl() + emailDto.getToken());
        confirmationTokenService.sendEmail(sm);
    }

}
