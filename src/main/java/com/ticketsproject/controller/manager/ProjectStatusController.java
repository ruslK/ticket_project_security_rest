//package com.ticketsproject.controller.manager;
//
//import com.ticketsproject.dto.ProjectDTO;
//import com.ticketsproject.dto.TaskDTO;
//import com.ticketsproject.dto.UserDTO;
//import com.ticketsproject.enums.Status;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Controller
//@RequestMapping("/manager")
//public class ProjectStatusController {
//
//    TaskService taskService;
//    ProjectService projectService;
//    UserService userService;
//
//    public ProjectStatusController(TaskService taskService, ProjectService projectService, UserService userService) {
//        this.taskService = taskService;
//        this.projectService = projectService;
//        this.userService = userService;
//    }
//
//    @GetMapping("/status")
//    public String getListProjectStatuses(Model model) {
//        UserDTO manager = userService.findById("tina@gmail.com");
//
//
//
//        model.addAttribute("projects", getCountedListOfProjectsDTO(manager));
//        model.addAttribute("tasks", taskService);
//        return "manager/projectStatus";
//    }
//
//    private List<ProjectDTO> getCountedListOfProjectsDTO(UserDTO manager) {
//        List<ProjectDTO> projects = projectService.findAll().stream()
//                .filter(p -> p.getAssignedManager().equals(manager) )
//                .map(m -> {
//                    List<TaskDTO> taskList = taskService.findTasksByManager(manager);
//                    int completeCount = (int) taskList.stream()
//                            .filter(t -> t.getProject().equals(m) && t.getStatus() == Status.COMPLETE).count();
//                    int inCompleteCount = (int) taskList.stream()
//                            .filter(t -> t.getProject().equals(m) && t.getStatus() != Status.COMPLETE).count();
//                    return new ProjectDTO(
//                            m.getProjectCode(),
//                            m.getProjectName(),
//                            userService.findById(m.getAssignedManager().getUserName()),
//                            m.getStartDate(),
//                            m.getEndDate(),
//                            m.getProjectDetails(),
//                            m.getProjectStatus(),
//                            completeCount,
//                            inCompleteCount
//                            );
//                }).collect(Collectors.toList());
//        return projects;
//
//    }
//
//    @GetMapping("/status/update/{id}")
//    public String completeProject(@PathVariable String id) {
//        projectService.findById(id).setProjectStatus(Status.COMPLETE);
//        return "redirect:/manager/status";
//    }
//}
