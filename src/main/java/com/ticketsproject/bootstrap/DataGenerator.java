package com.ticketsproject.bootstrap;

import com.ticketsproject.dto.ProjectDTO;
import com.ticketsproject.dto.RoleDTO;
import com.ticketsproject.dto.UserDTO;
import com.ticketsproject.enums.Gender;
import com.ticketsproject.enums.Status;
import com.ticketsproject.servises.ProjectService;
import com.ticketsproject.servises.RoleService;
import com.ticketsproject.servises.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataGenerator implements CommandLineRunner {


    RoleService roleService;
    UserService userService;
    ProjectService projectService;

    @Autowired
    public DataGenerator(RoleService roleService, UserService userService, ProjectService projectService) {
        this.roleService = roleService;
        this.userService = userService;
        this.projectService = projectService;
    }

    @Override
    public void run(String... args) throws Exception {
        roleService.save(new RoleDTO(1L, "Administrator"));
        roleService.save(new RoleDTO(2L, "Manager"));
        roleService.save(new RoleDTO(3L, "Employee"));

        UserDTO u1 = new UserDTO("Job", "Misha", "job@misha.com", "password", true, "234523452345", roleService.findById(1L), Gender.FEMALE);
        UserDTO u2 = new UserDTO("Tina", "Ivanko", "tina@gmail.com", "password", true, "4356435643564356", roleService.findById(2L), Gender.FEMALE);
        UserDTO u3 = new UserDTO("Steve", "Shapiro", "steve@shapiro.com", "password", true, "4353454356", roleService.findById(3L), Gender.FEMALE);
        userService.save(u1);
        userService.save(u2);
        userService.save(u3);

        ProjectDTO p1 = new ProjectDTO("Spring MVC", "PR001", u2, LocalDate.now(), LocalDate.now().plusDays(25), "Project 1", Status.COMPLETE);
        ProjectDTO p2 = new ProjectDTO("Spring MVC 2", "PR002", u2, LocalDate.now(), LocalDate.now().plusDays(35), "Project 2", Status.IN_PROGRESS);
        ProjectDTO p3 = new ProjectDTO("Spring MVC 3", "PR003", u2, LocalDate.now(), LocalDate.now().plusDays(45), "Project 3", Status.UAT_TEST);
        projectService.save(p1);
        projectService.save(p2);
        projectService.save(p3);
    }
}
