package com.ticketsproject.controller.administrator;

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
import org.apache.logging.log4j.message.SimpleMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/administrator")
@Tag(name = "Administrator Controller", description = "API for Administration")
public class ManageUsersController {

    private final RoleService roleService;
    private final UserService userService;
    private final ConfirmationTokenService confirmationTokenService;
    private final MapperUtil mapperUtil;

    public ManageUsersController(RoleService roleService, UserService userService, ConfirmationTokenService confirmationTokenService, MapperUtil mapperUtil) {
        this.roleService = roleService;
        this.userService = userService;
        this.confirmationTokenService = confirmationTokenService;
        this.mapperUtil = mapperUtil;
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
