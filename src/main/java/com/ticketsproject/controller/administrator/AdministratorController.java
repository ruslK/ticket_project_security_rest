package com.ticketsproject.controller.administrator;

import com.ticketsproject.datagenerator.DataGenerator;
import com.ticketsproject.entities.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/administrator")
public class AdministratorController {

    DataGenerator data = new DataGenerator();

    @GetMapping("/users")
    public String getCreateUserPage(Model model) {
        model.addAttribute("newUser", new User());
        model.addAttribute("allUsers", data.getListOfUsers());
        model.addAttribute("roles", DataGenerator.ROLES);
        return "administrator/createUser";
    }

    @PostMapping("/users")
    public String postUsers(@ModelAttribute("newUser") User newUser, Model model) {
        data.setListOfUsers(newUser);
        model.addAttribute("newUser", new User());
        model.addAttribute("allUsers", data.getListOfUsers());
        model.addAttribute("roles", DataGenerator.ROLES);
        return "administrator/createUser";
    }

    @GetMapping("/projects")
    public String getCreateProjectsPage() {
        return "administrator/createProject";
    }

}
