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

    @Value("${app.local-url}")
    private String BASE_URL;

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

    @GetMapping("/users")
    @Operation(summary = "Get List of Users", description = "Get List of all Users in the Application")
    public ResponseEntity<ResponseWrapper> getCreateUserPage() {
        return ResponseEntity
                .ok(new ResponseWrapper("List of the Users", userService.listAllUsers()));
    }

    @GetMapping("/users2")
    @Operation(summary = "Get List of Users", description = "Get List of all Users in the Application")
    public List<UserDTO> getCreateUserPage2() {
        return userService.listAllUsers();
    }

    @PostMapping("/create-user")
    @Operation(summary = "Create an User", description = "Creating a new User")
    public ResponseEntity<ResponseWrapper> postUsers(@RequestBody UserDTO newUser) {
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
                .url(BASE_URL + "/api/v1/administrator/confirmation?token=")
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

    @RequestMapping(value = "/confirmation", method = RequestMethod.GET)
    @Operation(summary = "Confirm Account", description = "Confirmation of Email")
    public ResponseEntity<ResponseWrapper> confirmEmail(@RequestParam(value = "token") String token) throws TicketingProjectException {
        ConfirmationToken confirmationToken = confirmationTokenService.readByToken(token);
        UserDTO confirmUser = userService.confirm(confirmationToken.getUser());
        confirmationTokenService.delete(confirmationToken);
        return ResponseEntity.ok(new ResponseWrapper("User has been Confirmed", confirmUser));
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
