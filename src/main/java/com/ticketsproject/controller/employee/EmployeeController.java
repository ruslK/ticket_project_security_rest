package com.ticketsproject.controller.employee;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/employee")
public class EmployeeController {

    @GetMapping("/tasks")
    public String getCreateProjectsPage() {
        return "employee/pendingTask";
    }

    @GetMapping("/archive")
    public String getCreateUserPage() {
        return "employee/archive";
    }

}
