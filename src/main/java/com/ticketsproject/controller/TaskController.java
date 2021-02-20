package com.ticketsproject.controller;

import com.ticketsproject.dto.TaskDTO;
import com.ticketsproject.entities.ResponseWrapper;
import com.ticketsproject.enums.Status;
import com.ticketsproject.exception.TicketingProjectException;
import com.ticketsproject.servises.ProjectService;
import com.ticketsproject.servises.TaskService;
import com.ticketsproject.servises.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/task")
@Tag(name = "Task Controller", description = "API for Tasks")
public class TaskController {

    ProjectService projectService;
    UserService userService;
    TaskService taskService;

    public TaskController(ProjectService projectService, UserService userService, TaskService taskService) {
        this.projectService = projectService;
        this.userService = userService;
        this.taskService = taskService;
    }

    @GetMapping
    @Operation(summary = "Get All Task", description = "Get list of All Task")
    @PreAuthorize("hasAuthority('Manager')")
    public ResponseEntity<ResponseWrapper> readAllTasks() {
        return ResponseEntity.ok(new ResponseWrapper("All Tasks", taskService.listOfTasks()));
    }

    @GetMapping("/project-manager")
    @Operation(summary = "Get All Tasks by Project Manager")
    @PreAuthorize("hasAuthority('Manager')")
    public ResponseEntity<ResponseWrapper> readAllTasksByProjectManager() throws TicketingProjectException {
        List<TaskDTO> taskDTOList = taskService.listAllTaskByProjectManager();
        return ResponseEntity.ok(new ResponseWrapper("All Tasks by Manager", taskDTOList));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Read Task By Id")
    @PreAuthorize("hasAnyAuthority('Employee', 'Manager')")
    public ResponseEntity<ResponseWrapper> readById(@PathVariable("id") Long id) throws TicketingProjectException {
        TaskDTO currentTask = taskService.findTaskById(id);
        return ResponseEntity.ok(new ResponseWrapper("Successfully Retrieved Task", currentTask));

    }

    @PostMapping
    @Operation(summary = "Create Task")
    @PreAuthorize("hasAuthority( 'Manager')")
    public ResponseEntity<ResponseWrapper> create(@RequestBody TaskDTO taskDTO) {
        TaskDTO createTask = taskService.save(taskDTO);
        return ResponseEntity.ok(new ResponseWrapper("Task CreatedSuccessfully", createTask));

    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Task By Id")
    @PreAuthorize("hasAnyAuthority('Manager')")
    public ResponseEntity<ResponseWrapper> deleteById(@PathVariable("id") Long id) throws TicketingProjectException {
        taskService.deleteByID(id);
        return ResponseEntity.ok(new ResponseWrapper("Task deleted"));
    }

    @PutMapping
    @Operation(summary = "Update Task By Id")
    @PreAuthorize("hasAnyAuthority('Manager', 'Employee')")
    public ResponseEntity<ResponseWrapper> updateByManager(@RequestBody TaskDTO taskDTO) throws TicketingProjectException {
        return ResponseEntity
                .ok(new ResponseWrapper("Successfully Retrieved Task", taskService.update(taskDTO)));

    }

    @GetMapping("/employee")
    @Operation(summary = "Get All Non Completed Tasks")
    @PreAuthorize("hasAuthority('Employee')")
    public ResponseEntity<ResponseWrapper> employeeReadAllNonCompleteTasks() throws TicketingProjectException {
        return ResponseEntity
                .ok(new ResponseWrapper("List of Non Completed Tasks",
                        taskService.listAllTaskByStatusIsNot(Status.COMPLETE)));
    }


//    @PutMapping
//    @Operation(summary = "Update Task By Employee")
//    @PreAuthorize("hasAuthority('Manager')")
//    public ResponseEntity<ResponseWrapper> employeeUpdateByTask(@RequestBody TaskDTO taskDTO) throws TicketingProjectException {
//        return ResponseEntity
//                .ok(new ResponseWrapper("Successfully Retrieved Task", taskService.updateStatus(taskDTO)));
//
//    }


}
