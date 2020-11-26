package com.ticketsproject.controller.administrator;

import com.ticketsproject.dto.ProjectDTO;
import com.ticketsproject.entities.User;
import com.ticketsproject.enums.Status;
import com.ticketsproject.servises.ProjectService;
import com.ticketsproject.servises.UserService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Component
@Controller
@RequestMapping("/administrator")
public class ManageProjectsController {

    ProjectService projectService;
    UserService userService;

    public ManageProjectsController(ProjectService projectService, UserService userService) {
        this.projectService = projectService;
        this.userService = userService;
    }

    @GetMapping("/projects")
    public String getCreateProject(Model model) {
        model.addAttribute("newProject", new ProjectDTO());
        model.addAttribute("managers", userService.findAll());
        model.addAttribute("allProject", projectService.findAll());
        return "administrator/createProject";
    }

    @PostMapping("/projects")
    public String postNewProject(@ModelAttribute ProjectDTO newProject) {
        projectService.save(newProject);
        newProject.setProjectStatus(Status.OPEN);
        return "redirect:/administrator/projects";
    }

    @GetMapping("/projects/delete/{projectCode}")
    public String deleteProject(@PathVariable("projectCode") String projectCode) {
        projectService.delete(projectService.findById(projectCode));
        return "redirect:/administrator/projects";
    }

    @GetMapping("/projects/update/{projectCode}")
    public String updateProject(@PathVariable("projectCode") String projectCode, Model model) {
        model.addAttribute("newProject", projectService.findById(projectCode));
        model.addAttribute("managers", userService.findAll());
        model.addAttribute("allProject", projectService.findAll());
        return "administrator/createProject";
    }

    @GetMapping("/projects/complete/{projectCode}")
    public String completeProject(@PathVariable("projectCode") String projectCode) {
        projectService.findById(projectCode).setProjectStatus(Status.COMPLETE);
        return "redirect:/administrator/projects";
    }

}
