package com.ticketsproject.controller;


import com.ticketsproject.dto.RoleDTO;
import com.ticketsproject.entities.ResponseWrapper;
import com.ticketsproject.servises.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/role")
@Tag(name = "Role Controller", description = "API for Roles")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    @Operation(summary = "Get All Role", description = "Get list of All Roles")
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<ResponseWrapper> readAllRoles() {
        List<RoleDTO> roleDTOList = roleService.listAllRoles();
        return ResponseEntity.ok(new ResponseWrapper("Successfully Retrieved all Roles", roleDTOList));
    }
}
