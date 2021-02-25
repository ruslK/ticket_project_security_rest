package com.ticketsproject.controller;

import com.ticketsproject.annotation.ExecutionTime;
import com.ticketsproject.dto.UserDTO;
import com.ticketsproject.entities.ConfirmationToken;
import com.ticketsproject.entities.ResponseWrapper;
import com.ticketsproject.exception.TicketingProjectException;
import com.ticketsproject.servises.ConfirmationTokenService;
import com.ticketsproject.servises.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Confirmation Email", description = "API for Confirmation Email")
public class ConfirmationController {

    public ConfirmationController(ConfirmationTokenService confirmationTokenService, UserService userService) {
        this.confirmationTokenService = confirmationTokenService;
        this.userService = userService;
    }

    private final ConfirmationTokenService confirmationTokenService;
    private final UserService userService;

    @RequestMapping(value = "/confirmation", method = RequestMethod.GET)
    @Operation(summary = "Confirm Account", description = "Confirmation of Email")
    @ExecutionTime
    public ResponseEntity<ResponseWrapper> confirmEmail(@RequestParam(value = "token") String token) throws TicketingProjectException {
        ConfirmationToken confirmationToken = confirmationTokenService.readByToken(token);
        UserDTO confirmUser = userService.confirm(confirmationToken.getUser());
        confirmationTokenService.delete(confirmationToken);
        return ResponseEntity.ok(new ResponseWrapper("User has been Confirmed", confirmUser));
    }
}
