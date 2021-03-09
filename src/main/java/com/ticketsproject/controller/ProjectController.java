package com.ticketsproject.controller;

import com.ticketsproject.annotation.ExecutionTime;
import com.ticketsproject.dto.ProjectDTO;
import com.ticketsproject.entities.Project;
import com.ticketsproject.entities.ResponseWrapper;
import com.ticketsproject.exception.AccessDeniedException;
import com.ticketsproject.exception.TicketingProjectException;
import com.ticketsproject.servises.ProjectService;
import com.ticketsproject.servises.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

@RestController
@RequestMapping("/api/v1/project")
@Tag(name = "Project Controller", description = "API for Project")
public class ProjectController {

    ProjectService projectService;
    UserService userService;

    public ProjectController(ProjectService projectService, UserService userService) {
        this.projectService = projectService;
        this.userService = userService;
    }

    @GetMapping
    @Operation(summary = "Get All Project", description = "Get list of All Project")
    @PreAuthorize("hasAnyAuthority('Admin', 'Manager')")
    @ExecutionTime
    public ResponseEntity<ResponseWrapper> readAll() {
        return ResponseEntity.ok(new ResponseWrapper("All Project", projectService.listOfProjects()));
    }

    @GetMapping("/{projectCode}")
    @Operation(summary = "Read by Project Code", description = "Read by Project Code")
    @PreAuthorize("hasAnyAuthority('Admin', 'Manager')")
    public ResponseEntity<ResponseWrapper> readByProjectCode(@PathVariable("projectCode") String projectCode) throws TicketingProjectException {
        return ResponseEntity.ok(new ResponseWrapper("Project is retrieved", projectService.findByProjectCode(projectCode)));
    }

    @PostMapping
    @Operation(summary = "Create Project", description = "Create Project")
    @PreAuthorize("hasAnyAuthority('Admin', 'Manager')")
    public ResponseEntity<ResponseWrapper> createProject(@RequestBody ProjectDTO project) throws TicketingProjectException, AccessDeniedException {
        System.out.println("++++++++++++++++++++");
        return ResponseEntity.ok(new ResponseWrapper("Project created", projectService.save(project)));
    }

    @DeleteMapping("/{projectcode}")
    @Operation(summary = "Delete by Project Code", description = "Delete by Project Code")
    @PreAuthorize("hasAnyAuthority('Admin', 'Manager')")
    public ResponseEntity<ResponseWrapper> deleteByProjectCode(@PathVariable("projectcode") String projectcode) throws TicketingProjectException {
        projectService.deleteByProjectCode(projectcode);
        return ResponseEntity.ok(new ResponseWrapper("Project is deleted"));
    }

    @PutMapping("/projectcode")
    @Operation(summary = "Update by Project Code", description = "Update by Project Code")
    @PreAuthorize("hasAnyAuthority('Admin', 'Manager')")
    public ResponseEntity<ResponseWrapper> updateByProjectCode(@RequestBody ProjectDTO projectDTO) throws TicketingProjectException, AccessDeniedException {
        return ResponseEntity.ok(new ResponseWrapper("Project is deleted", projectService.update(projectDTO)));
    }

    @PutMapping("/complete/projectcode")
    @Operation(summary = "Complete by Project Code", description = "Complete by Project Code")
    @PreAuthorize("hasAuthority('Manager')")
    public ResponseEntity<ResponseWrapper> completeByProjectCode(@PathVariable("projectcode") String projectcode) {
        projectService.complete(projectcode);
        return ResponseEntity.ok(new ResponseWrapper("Project is completed"));
    }

    @GetMapping("/details")
    @Operation(summary = "Read Project Details", description = "Read Project Details")
    @PreAuthorize("hasAuthority('Manager')")
    public ResponseEntity<ResponseWrapper> readAllProjectDetails() {
        return ResponseEntity.ok(new ResponseWrapper("Project details retrieved", projectService.getAllProjectByManagerId()));
    }


}
