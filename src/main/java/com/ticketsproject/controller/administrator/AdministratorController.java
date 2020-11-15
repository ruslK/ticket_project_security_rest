package com.ticketsproject.controller.administrator;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/administrator")
public class AdministratorController {

    @GetMapping("/users")
    public String getCreateUserPage() {
        return "administrator/createUser";
    }

    @GetMapping("/projects")
    public String getCreateProjectsPage() {
        return "administrator/createProject";
    }
}
