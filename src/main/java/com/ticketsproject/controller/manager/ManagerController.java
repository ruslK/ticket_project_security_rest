package com.ticketsproject.controller.manager;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/manager")
public class ManagerController {

    @GetMapping("/projects")
    public String getCreateUserPage() {
        return "manager/projectStatus";
    }

    @GetMapping("/tasks")
    public String getCreateProjectsPage() {
        return "manager/taskAssign";
    }
}
