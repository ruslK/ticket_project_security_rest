package com.ticketsproject.controller.administrator;

import com.ticketsproject.servises.RoleService;
import com.ticketsproject.servises.UserService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Component
@Controller
@RequestMapping("/administrator")
public class ManageProjectsController {

    RoleService roleService;
    UserService userService;

    public ManageProjectsController(RoleService roleService, UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    @GetMapping("/projects")
    public String getCreateProjectsPage() {
        return "administrator/createProject";
    }

}
