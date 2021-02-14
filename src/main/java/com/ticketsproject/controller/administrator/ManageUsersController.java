package com.ticketsproject.controller.administrator;

import com.ticketsproject.dto.UserDTO;
import com.ticketsproject.entities.ResponseWrapper;
import com.ticketsproject.exception.TicketingProjectException;
import com.ticketsproject.servises.RoleService;
import com.ticketsproject.servises.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/administrator")
@Tag(name = "Administrator Controller", description = "API for Administration")
public class ManageUsersController {

    private final RoleService roleService;
    private final UserService userService;

    public ManageUsersController(RoleService roleService, UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    @GetMapping("/users")
    @Operation(summary = "Get List of Users", description = "Get List of all Users in the Application")
    public ResponseEntity<ResponseWrapper> getCreateUserPage() {
        return ResponseEntity.ok(new ResponseWrapper("List of the Users", userService.listAllUsers()));
    }

    @GetMapping("/users2")
    @Operation(summary = "Get List of Users", description = "Get List of all Users in the Application")
    public List<UserDTO> getCreateUserPage2() {
        return userService.listAllUsers();
    }

    @PostMapping("/users")
    @Operation(summary = "Create an User", description = "Creating a new User")
    public ResponseEntity<ResponseWrapper> postUsers(@RequestBody UserDTO newUser) {
        System.out.println(newUser);
        userService.save(newUser);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ResponseWrapper.builder()
                        .message("User created")
                        .code(201)
                        .success(true)
                        .data(userService.findByUserName(newUser.getUserName()))
                        .build());
    }
//
//    @GetMapping("/users/update/{id}")
//    public String updateUser(@PathVariable("id") String id, Model model) {
//        model.addAttribute("newUser", userService.findByUserName(id));
//        model.addAttribute("allUsers", userService.listAllUsers());
//        model.addAttribute("roles", roleService.listAllRoles());
//        return "administrator/createUser";
//    }
//
//    @GetMapping("/users/delete/{id}")
//    public String deleteUser(@PathVariable("id") String id) {
//        try {
//            userService.delete(id);
//            return "redirect:/administrator/users";
//        } catch (TicketingProjectException e) {
//            e.printStackTrace();
//            return "redirect:/administrator/users";
//        }
//
//    }
}
