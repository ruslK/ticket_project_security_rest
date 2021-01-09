package com.ticketsproject.controller.manager;

import com.ticketsproject.mapper.ProjectMapper;
import com.ticketsproject.servises.ProjectService;
import com.ticketsproject.servises.TaskService;
import com.ticketsproject.servises.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/manager")
public class ProjectStatusController {

    private final TaskService taskService;
    private final ProjectService projectService;
    private final UserService userService;
    private final ProjectMapper projectMapper;


    public ProjectStatusController(TaskService taskService, ProjectService projectService, UserService userService, ProjectMapper projectMapper) {
        this.taskService = taskService;
        this.projectService = projectService;
        this.userService = userService;
        this.projectMapper = projectMapper;
    }

    @GetMapping("/status")
    public String getListProjectStatuses(Model model) {
        model.addAttribute("projects", projectService.getAllProjectByManagerId());
        return "manager/projectStatus";
    }


    @GetMapping("/status/update/{projectCode}")
    public String completeProject(@PathVariable String projectCode) {
        projectService.complete(projectCode);
        return "redirect:/manager/status";
    }
}
